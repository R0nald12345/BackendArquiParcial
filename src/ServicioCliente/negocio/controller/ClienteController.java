
package ServicioCliente.negocio.controller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import ServicioCliente.negocio.ClienteN;
import ServicioCliente.datos.entidades.Cliente;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.List;

/**
 *
 * @author USER
 * 
 */

public class ClienteController {
    
    // Handler para /clientes (GET listar, POST crear)
    public static class ClienteHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            try {
                ClienteN service = new ClienteN();
                Gson gson = new Gson();

                if ("GET".equals(exchange.getRequestMethod())) {
                    List<Cliente> lista = service.listarClientes();
                    String response = gson.toJson(lista);
                    sendResponse(exchange, 200, response);

                } else if ("POST".equals(exchange.getRequestMethod())) {
                    BufferedReader reader = new BufferedReader(
                        new InputStreamReader(exchange.getRequestBody(), "UTF-8")
                    );
                    Cliente c = gson.fromJson(reader, Cliente.class);

                    boolean ok = service.crearCliente(c);
                    String response = gson.toJson(ok ? "Cliente creado" : "Error al crear");
                    sendResponse(exchange, ok ? 201 : 400, response);

                } else {
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                }

            } catch (Exception e) {
                handleError(exchange, e);
            }
        }
    }

    // Handler para /clientes/{id} (GET, PUT, DELETE)
    public static class ClienteIdHandler implements HttpHandler {
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

                ClienteN service = new ClienteN();
                Gson gson = new Gson();

                switch (exchange.getRequestMethod()) {
                    case "GET":
                        Cliente c = service.obtenerCliente(id);
                        if (c != null) {
                            sendResponse(exchange, 200, gson.toJson(c));
                        } else {
                            sendResponse(exchange, 404, "{\"mensaje\":\"No encontrado\"}");
                        }
                        break;

                    case "PUT":
                        BufferedReader reader = new BufferedReader(
                            new InputStreamReader(exchange.getRequestBody(), "UTF-8")
                        );
                        Cliente clienteUpdate = gson.fromJson(reader, Cliente.class);
                        clienteUpdate.setId(id);

                        boolean actualizado = service.actualizarCliente(clienteUpdate);
                        sendResponse(exchange, actualizado ? 200 : 400,
                                gson.toJson(actualizado ? "Actualizado" : "Error al actualizar"));
                        break;

                    case "DELETE":
                        boolean eliminado = service.eliminarCliente(id);
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