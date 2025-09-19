/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioPedido.negocio.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ServicioPedido.negocio.PedidoN;
import ServicioPedido.datos.entidades.Pedido;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.List;

/**
 *
 * @author USER
 */
public class PedidoController {

    // Handler para /pedidos (GET listar, POST crear)
    public static class PedidoHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) {
            try {
                PedidoN service = new PedidoN();
                Gson gson = new Gson();

                if ("GET".equals(exchange.getRequestMethod())) {
                    List<Pedido> lista = service.listarPedidos();
                    String response = gson.toJson(lista);
                    sendResponse(exchange, 200, response);

                } else if ("POST".equals(exchange.getRequestMethod())) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                    Pedido pedido = gson.fromJson(reader, Pedido.class);

                    boolean ok = service.crearPedido(pedido);
                    String response = gson.toJson(ok ? "Pedido creado" : "Error al crear");
                    sendResponse(exchange, ok ? 201 : 400, response);

                } else {
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                }
            } catch (Exception e) {
                handleError(exchange, e);
            }
        }
    }

    // Handler para /pedidos/{id} (GET, PUT, DELETE)
    public static class PedidoIdHandler implements HttpHandler {

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

                PedidoN service = new PedidoN();
                Gson gson = new Gson();

                switch (exchange.getRequestMethod()) {
                    case "GET":
                        Pedido pedido = service.obtenerPedido(id);
                        if (pedido != null) {
                            sendResponse(exchange, 200, gson.toJson(pedido));
                        } else {
                            sendResponse(exchange, 404, "{\"mensaje\":\"No encontrado\"}");
                        }
                        break;

                    case "PUT":
                        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                        Pedido pedidoActualizar = gson.fromJson(reader, Pedido.class);
                        pedidoActualizar.setId(id);
                        boolean actualizado = service.actualizarPedido(pedidoActualizar);
                        sendResponse(exchange, actualizado ? 200 : 400,
                                gson.toJson(actualizado ? "Pedido actualizado" : "Error al actualizar"));
                        break;

                    case "DELETE":
                        boolean eliminado = service.eliminarPedido(id);
                        sendResponse(exchange, eliminado ? 200 : 400,
                                gson.toJson(eliminado ? "Pedido eliminado" : "Error al eliminar"));
                        break;

                    default:
                        exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                        break;
                }

            } catch (Exception e) {
                handleError(exchange, e);
            }
        }
    }

    // ===== Helpers =====
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
