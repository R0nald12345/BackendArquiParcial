
package ServicioPedido.negocio.controller;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

/**
 *
 * @author USER
 */


public class PedidoServidor {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8084), 0);

        server.createContext("/pedidos", new PedidoController.PedidoHandler());
        server.createContext("/pedidos/", new PedidoController.PedidoIdHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor de Pedidos iniciado en http://localhost:8084");
        System.out.println(" -> /pedidos   (GET, POST)");
        System.out.println(" -> /pedidos/{id} (GET)");
    }
}
