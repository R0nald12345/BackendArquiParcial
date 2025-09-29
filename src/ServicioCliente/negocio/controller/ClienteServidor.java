
package ServicioCliente.negocio.controller;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

/**
 *
 * @author USER
 */
public class ClienteServidor {
    
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8083), 0);

        // Endpoints
        server.createContext("/clientes", new ClienteController.ClienteHandler());
        server.createContext("/clientes/", new ClienteController.ClienteIdHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor de Clientes iniciado en http://localhost:8083");
        System.out.println(" -> /clientes   (GET, POST)");
        System.out.println(" -> /clientes/{id} (GET, PUT, DELETE)");
    }
}