/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioRegistroCategoria.negocio.controller;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

/**
 *
 * @author USER
 */


public class CategoriaServidor {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/categorias", new CategoriaController.CategoriaHandler());
        server.createContext("/categorias/", new CategoriaController.CategoriaIdHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor de Categorías iniciado en http://localhost:8080");
        System.out.println(" -> /categorias   (GET, POST)");
        System.out.println(" -> /categorias/{id} (GET, PUT, DELETE)");
    }
}