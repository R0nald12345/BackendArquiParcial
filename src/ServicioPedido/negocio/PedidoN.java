package ServicioPedido.negocio;

import ServicioPedido.datos.PedidoDao;
import ServicioPedido.datos.DetallePedidoDao;
import ServicioPedido.datos.entidades.Pedido;
import ServicioPedido.datos.entidades.DetallePedido;
import java.util.List;

/**
 *
 * @author USER
 */
public class PedidoN {

    private final PedidoDao pedidoDao = new PedidoDao();
    private final DetallePedidoDao detalleDao = new DetallePedidoDao();

    public boolean crearPedido(Pedido pedido, List<DetallePedido> detalles) throws Exception {
        int pedidoId = pedidoDao.crear(pedido);
        if (pedidoId > 0) {
            for (DetallePedido dp : detalles) {
                dp.setPedidoId(pedidoId);
                detalleDao.crear(dp);
            }
            return true;
        }
        return false;
    }

    public Pedido obtenerPedido(int id) throws Exception {
        Pedido pedido = pedidoDao.obtenerPorId(id);
        if (pedido != null) {
            List<DetallePedido> detalles = detalleDao.listarPorPedido(id);
            // acá no lo guardo dentro del objeto Pedido, solo lo retorno aparte si querés
        }
        return pedido;
    }

    public List<Pedido> listarPedidos() throws Exception {
        return pedidoDao.listar();
    }

    public boolean actualizarPedido(Pedido pedido, List<DetallePedido> detalles) throws Exception {
        boolean ok = pedidoDao.actualizar(pedido);
        if (ok) {
            // Eliminar y volver a insertar los detalles
            detalleDao.eliminarPorPedido(pedido.getId());
            for (DetallePedido dp : detalles) {
                dp.setPedidoId(pedido.getId());
                detalleDao.crear(dp);
            }
        }
        return ok;
    }

    public boolean eliminarPedido(int id) throws Exception {
        return pedidoDao.eliminar(id);
    }
}
