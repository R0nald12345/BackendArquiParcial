
package ServiciosMetodoPago.negocio.controller;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

/**
 *
 * @author USER
 */
public class MetodoPagoServidor {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);

        server.createContext("/metodospago", new MetodoPagoController.MetodoPagoHandler());
        server.createContext("/metodospago/", new MetodoPagoController.MetodoPagoIdHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor de Métodos de Pago iniciado en http://localhost:8082");
        System.out.println(" -> /metodospago   (GET, POST)");
        System.out.println(" -> /metodospago/{id} (GET, PUT, DELETE)");
    }
}