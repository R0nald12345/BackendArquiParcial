
package ServicioPedido.datos;

import ServicioPedido.datos.entidades.DetallePedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class DetallePedidoDao {

     public boolean crear(DetallePedido dp) throws Exception {
        String sql = "INSERT INTO detallePedido(pedido_id, producto_id, cantidad, precio) VALUES(?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dp.getPedidoId());
            ps.setInt(2, dp.getProductoId());
            ps.setInt(3, dp.getCantidad());
            ps.setDouble(4, dp.getPrecio());
            return ps.executeUpdate() > 0;
        }
    }

    public List<DetallePedido> listarPorPedido(int pedidoId) throws Exception {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM detallePedido WHERE pedido_id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pedidoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetallePedido dp = new DetallePedido();
                dp.setPedidoId(rs.getInt("pedido_id"));
                dp.setProductoId(rs.getInt("producto_id"));
                dp.setCantidad(rs.getInt("cantidad"));
                dp.setPrecio(rs.getDouble("precio"));
                lista.add(dp);
            }
        }
        return lista;
    }
    
       // 🔹 Nuevo método que te faltaba
    public boolean eliminarPorPedido(int pedidoId) throws Exception {
        String sql = "DELETE FROM detallePedido WHERE pedido_id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pedidoId);
            return ps.executeUpdate() > 0;
        }
    }
}