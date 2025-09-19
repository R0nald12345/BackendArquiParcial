/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioRepartidor.negocio.controller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import ServicioRepartidor.negocio.RepartidorN;
import ServicioRepartidor.datos.entidades.Repartidor;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.List;

/**
 *
 * @author USER
 */
public class RepartidorController {

    public static class RepartidorHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) {
            try {
                RepartidorN service = new RepartidorN();
                Gson gson = new Gson();

                if ("GET".equals(exchange.getRequestMethod())) {
                    List<Repartidor> lista = service.listar();
                    sendResponse(exchange, 200, gson.toJson(lista));
                } else if ("POST".equals(exchange.getRequestMethod())) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                    Repartidor r = gson.fromJson(reader, Repartidor.class);

                    boolean ok = service.crear(r);
                    sendResponse(exchange, ok ? 201 : 400,
                            gson.toJson(ok ? "Repartidor creado" : "Error al crear"));
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
            } catch (Exception e) {
                handleError(exchange, e);
            }
        }
    }

    public static class RepartidorIdHandler implements HttpHandler {

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

                RepartidorN service = new RepartidorN();
                Gson gson = new Gson();

                switch (exchange.getRequestMethod()) {
                    case "GET":
                        Repartidor r = service.obtener(id);
                        sendResponse(exchange, r != null ? 200 : 404,
                                r != null ? gson.toJson(r) : "{\"mensaje\":\"No encontrado\"}");
                        break;
                    case "PUT":
                        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                        Repartidor rUpdate = gson.fromJson(reader, Repartidor.class);
                        rUpdate.setId(id);
                        boolean actualizado = service.actualizar(rUpdate);
                        sendResponse(exchange, actualizado ? 200 : 400,
                                gson.toJson(actualizado ? "Actualizado" : "Error al actualizar"));
                        break;
                    case "DELETE":
                        boolean eliminado = service.eliminar(id);
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
