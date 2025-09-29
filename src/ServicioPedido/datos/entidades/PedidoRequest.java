/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioPedido.datos.entidades;
import ServicioPedido.datos.entidades.Pedido;
import ServicioPedido.datos.entidades.DetallePedido;
import java.util.List;
/**
 *
 * @author USER
 */
public class PedidoRequest {
    public Pedido pedido;
    public List<DetallePedido> detalles;
}
