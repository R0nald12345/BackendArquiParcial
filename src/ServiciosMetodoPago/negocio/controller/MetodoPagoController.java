/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiciosMetodoPago.negocio.controller;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import ServiciosMetodoPago.negocio.MetodoPagoN;
import ServiciosMetodoPago.datos.entidades.MetodoPago;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.List;


/**
 *
 * @author USER
 */
public class MetodoPagoController {
     // Handler para /metodospago (GET listar, POST crear)
    public static class MetodoPagoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            try {
                MetodoPagoN service = new MetodoPagoN();
                Gson gson = new Gson();

                if ("GET".equals(exchange.getRequestMethod())) {
                    List<MetodoPago> lista = service.listarMetodoPagos();
                    sendResponse(exchange, 200, gson.toJson(lista));
                } else if ("POST".equals(exchange.getRequestMethod())) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                    MetodoPago mp = gson.fromJson(reader, MetodoPago.class);

                    boolean ok = service.crearMetodoPago(mp);
                    sendResponse(exchange, ok ? 201 : 400,
                            gson.toJson(ok ? "Método de Pago creado" : "Error al crear"));
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
            } catch (Exception e) {
                handleError(exchange, e);
            }
        }
    }

    // Handler para /metodospago/{id} (GET, PUT, DELETE)
    public static class MetodoPagoIdHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            try {
                String path = exchange.getRequestURI().getPath();
                String[] parts = path.split("/");
                if (parts.length < 3) {
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }
                int id = Integer.parseInt(parts[2]);

                MetodoPagoN service = new MetodoPagoN();
                Gson gson = new Gson();

                switch (exchange.getRequestMethod()) {
                    case "GET":
                        MetodoPago mp = service.obtenerMetodoPago(id);
                        sendResponse(exchange, mp != null ? 200 : 404,
                                mp != null ? gson.toJson(mp) : "{\"mensaje\":\"No encontrado\"}");
                        break;
                    case "PUT":
                        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                        MetodoPago mpUpdate = gson.fromJson(reader, MetodoPago.class);
                        mpUpdate.setId(id);
                        boolean actualizado = service.actualizarMetodoPago(mpUpdate);
                        sendResponse(exchange, actualizado ? 200 : 400,
                                gson.toJson(actualizado ? "Actualizado" : "Error al actualizar"));
                        break;
                    case "DELETE":
                        boolean eliminado = service.eliminarMetodoPago(id);
                        sendResponse(exchange, eliminado ? 200 : 400,
                                gson.toJson(eliminado ? "Eliminado" : "Error al eliminar"));
                        break;
                    default:
                        exchange.sendResponseHeaders(405, -1);
                }
            } catch (Exception e) {
                handleError(exchange, e);
            }
        }
    }

    // Helpers
    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws Exception {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static void handleError(HttpExchange exchange, Exception e) {
        e.printStackTrace();
        try {
            String errorResponse = "{\"error\":\"" + e.getMessage() + "\"}";
            sendResponse(exchange, 500, errorResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
