package ServicioRegistroProducto.negocio.controller;

import ServicioRegistroProducto.negocio.controller.*;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import ServicioRegistroProducto.negocio.ProductoN;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.InetSocketAddress;
import ServicioRegistroProducto.datos.entidades.Producto;
import java.util.List;

/**
 *
 * @author Ronald
 */
public class ProductoController {

//    public static void main(String[] args) throws Exception {
//        HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);
//
//        // Endpoints REST
//        server.createContext("/productos", new ProductoHandler()); // GET lista, POST crear
//        server.createContext("/productos/", new ProductoIdHandler()); // GET/{id}, PUT/{id}, DELETE/{id}
//
//        server.setExecutor(null);
//        server.start();
//        System.out.println("Servidor Producto iniciado en http://localhost:8082/productos");
//    }
    // Handler para /productos (GET listar, POST crear)
    static class ProductoHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) {
            try {
                ProductoN service = new ProductoN();
                Gson gson = new Gson();

                if ("GET".equals(exchange.getRequestMethod())) {
                    // LISTAR TODOS
                    List<Producto> lista = service.listarProductos();
                    String response = gson.toJson(lista);
                    sendResponse(exchange, 200, response);
                } else if ("POST".equals(exchange.getRequestMethod())) {
                    // CREAR
                    BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                    Producto producto = gson.fromJson(reader, Producto.class);

                    boolean ok = service.crearProducto(producto);
                    String response = gson.toJson(ok ? "Producto creado" : "Error al crear");
                    sendResponse(exchange, ok ? 201 : 400, response);
                } else {
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                }
            } catch (Exception e) {
                handleError(exchange, e);
            }
        }
    }

    // Handler para /productos/{id} (GET, PUT, DELETE)
    static class ProductoIdHandler implements HttpHandler {

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

                ProductoN service = new ProductoN();
                Gson gson = new Gson();

                switch (exchange.getRequestMethod()) {
                    case "GET":
                        Producto p = service.obtenerProducto(id);
                        if (p != null) {
                            sendResponse(exchange, 200, gson.toJson(p));
                        } else {
                            sendResponse(exchange, 404, "{\"mensaje\":\"No encontrado\"}");
                        }
                        break;
                    case "PUT":
                        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                        Producto producto = gson.fromJson(reader, Producto.class);
                        producto.setId(id);
                        boolean actualizado = service.actualizarProducto(producto);
                        sendResponse(exchange, actualizado ? 200 : 400,
                                gson.toJson(actualizado ? "Actualizado" : "Error al actualizar"));
                        break;
                    case "DELETE":
                        boolean eliminado = service.eliminarProducto(id);
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

    // Handler para /productos/categoria/{categoriaId} (GET)
    // Handler para /productos/categoria/{categoriaId} (GET)
    static class ProductoPorCategoriaHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) {
            try {
                String path = exchange.getRequestURI().getPath();
                String[] parts = path.split("/");

                // /productos/categoria/{id} -> el id está en parts[3]
                if (parts.length < 4) {
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }
                int categoriaId = Integer.parseInt(parts[3]);

                ProductoN service = new ProductoN();
                Gson gson = new Gson();

                if ("GET".equals(exchange.getRequestMethod())) {
                    List<Producto> lista = service.listarProductosPorCategoria(categoriaId);
                    String response = gson.toJson(lista);
                    sendResponse(exchange, 200, response);
                } else {
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
