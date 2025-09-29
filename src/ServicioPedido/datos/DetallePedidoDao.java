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
        String sql = "SELECT dp.pedido_id, dp.producto_id, dp.cantidad, dp.precio, p.nombre AS productoNombre "
                + "FROM detallePedido dp "
                + "JOIN Producto p ON dp.producto_id = p.id "
                + "WHERE dp.pedido_id = ?";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pedidoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetallePedido dp = new DetallePedido(
                        rs.getInt("pedido_id"),
                        rs.getInt("producto_id"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getString("productoNombre") // ✅ ahora sí viene el nombre
                );
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
