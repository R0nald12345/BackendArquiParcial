/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioRegistroProducto.negocio.controller;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

/**
 * @author USER
*/


public class ProductoServidor {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/productos", new ProductoController.ProductoHandler());
        server.createContext("/productos/", new ProductoController.ProductoIdHandler());
        server.createContext("/productos/categoria/", new ProductoController.ProductoPorCategoriaHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Servidor de Productos iniciado en http://localhost:8081");
        System.out.println(" -> /productos   (GET, POST)");
        System.out.println(" -> /productos/{id} (GET, PUT, DELETE)");
    }
}