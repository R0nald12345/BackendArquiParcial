/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioPedido.negocio;

import ServicioPedido.datos.PedidoDao;
import ServicioPedido.datos.entidades.Pedido;
import java.util.List;

/**
 *
 * @author USER
 */
public class PedidoN {

    private final PedidoDao pedidoDao = new PedidoDao();

    public List<Pedido> listarPedidos() throws Exception {
        return pedidoDao.listar();
    }

    public boolean crearPedido(Pedido pedido) throws Exception {
        return pedidoDao.crear(pedido);
    }

    public Pedido obtenerPedido(int id) throws Exception {
        return pedidoDao.obtenerPorId(id);
    }

    public boolean actualizarPedido(Pedido pedido) throws Exception {
        return pedidoDao.actualizar(pedido);
    }

    public boolean eliminarPedido(int id) throws Exception {
        return pedidoDao.eliminar(id);
    }
}
