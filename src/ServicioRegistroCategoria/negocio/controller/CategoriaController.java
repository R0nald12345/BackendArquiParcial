
package ServicioRegistroCategoria.negocio.controller;

import ServicioRegistroCategoria.negocio.controller.*;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import ServicioRegistroCategoria.negocio.CategoriaN;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.InetSocketAddress;
import ServicioRegistroCategoria.datos.entidades.Categoria;
import java.util.List;

/**
 * @author Ronald Camino Puma
 * 
 */

public class CategoriaController {

//    public static void main(String[] args) throws Exception {
//        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
//
//        // Endpoints REST
//        server.createContext("/categorias", new CategoriaHandler()); // GET lista, POST crear
//        server.createContext("/categorias/", new CategoriaIdHandler()); // GET/{id}, PUT/{id}, DELETE/{id}
//
//        server.setExecutor(null);
//        server.start();
//        System.out.println("Servidor Categoria iniciado en http://localhost:8081/categorias");
//    }

    // Handler para /categorias (GET listar, POST crear)
    static class CategoriaHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) {
            try {
                CategoriaN service = new CategoriaN();
                Gson gson = new Gson();

                if ("GET".equals(exchange.getRequestMethod())) {
                    // LISTAR TODAS
                    List<Categoria> lista = service.listarCategorias();
                    String response = gson.toJson(lista);
                    sendResponse(exchange, 200, response);
                } else if ("POST".equals(exchange.getRequestMethod())) {
                    // CREAR
                    BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                    Categoria categoria = gson.fromJson(reader, Categoria.class);

                    boolean ok = service.crearCategoria(categoria);
                    String response = gson.toJson(ok ? "Categoría creada" : "Error al crear");
                    sendResponse(exchange, ok ? 201 : 400, response);
                } else {
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                }
            } catch (Exception e) {
                handleError(exchange, e);
            }
        }
    }

    // Handler para /categorias/{id} (GET, PUT, DELETE)
    static class CategoriaIdHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) {
            try {
                // Obtener id de la URL
                String path = exchange.getRequestURI().getPath();
                String[] parts = path.split("/");
                if (parts.length < 3) {
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }
                int id = Integer.parseInt(parts[2]);

                CategoriaN service = new CategoriaN();
                Gson gson = new Gson();

                switch (exchange.getRequestMethod()) {
                    case "GET":
                        Categoria c = service.obtenerCategoria(id);
                        if (c != null) {
                            sendResponse(exchange, 200, gson.toJson(c));
                        } else {
                            sendResponse(exchange, 404, "{\"mensaje\":\"No encontrado\"}");
                        }
                        break;
                    case "PUT":
                        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                        Categoria categoria = gson.fromJson(reader, Categoria.class);
                        categoria.setId(id);
                        boolean actualizado = service.actualizarCategoria(categoria);
                        sendResponse(exchange, actualizado ? 200 : 400,
                                gson.toJson(actualizado ? "Actualizado" : "Error al actualizar"));
                        break;
                    case "DELETE":
                        boolean eliminado = service.eliminarCategoria(id);
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
