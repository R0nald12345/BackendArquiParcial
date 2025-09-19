/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioRepartidor.negocio.controller;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

/**
 *
 * @author USER
 */
public class RepartidorServidor {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8085), 0);

        server.createContext("/repartidores", new RepartidorController.RepartidorHandler());
        server.createContext("/repartidores/", new RepartidorController.RepartidorIdHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor de Repartidores iniciado en http://localhost:8085");
        System.out.println(" -> /repartidores   (GET, POST)");
        System.out.println(" -> /repartidores/{id} (GET, PUT, DELETE)");
    }
}
