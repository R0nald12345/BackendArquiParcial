/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioPedido.datos;

import ServicioPedido.datos.entidades.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {

    public int crear(Pedido pedido) throws Exception {
        String sql = "INSERT INTO Pedido(monto, cliente_id, repartidor_id, metodoPago_id) VALUES(?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, pedido.getMonto());
            ps.setInt(2, pedido.getClienteId());

            // si no hay repartidor, seteamos null
            if (pedido.getRepartidorId() == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, pedido.getRepartidorId());
            }

            ps.setInt(4, pedido.getMetodoPagoId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        }
    }

    public Pedido obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Pedido WHERE id=?";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setFecha(rs.getString("fecha"));
                p.setMonto(rs.getDouble("monto"));
                p.setClienteId(rs.getInt("cliente_id"));
                p.setRepartidorId(rs.getInt("repartidor_id"));
                p.setMetodoPagoId(rs.getInt("metodoPago_id"));

                // 🔹 Traer detalles del pedido
                DetallePedidoDao dpDao = new DetallePedidoDao();
                p.setDetalles(dpDao.listarPorPedido(id));  // ✅ ahora sí se llena la lista

                return p;
            }
        }
        return null;
    }

    public List<Pedido> listar() throws Exception {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setFecha(rs.getString("fecha"));
                p.setMonto(rs.getDouble("monto"));
                p.setClienteId(rs.getInt("cliente_id"));
                p.setRepartidorId(rs.getInt("repartidor_id"));
                p.setMetodoPagoId(rs.getInt("metodoPago_id"));
                lista.add(p);
            }
        }
        return lista;
    }

    public boolean actualizar(Pedido pedido) throws Exception {
        String sql = "UPDATE Pedido SET monto=?, cliente_id=?, repartidor_id=?, metodoPago_id=? WHERE id=?";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, pedido.getMonto());
            ps.setInt(2, pedido.getClienteId());
            ps.setInt(3, pedido.getRepartidorId());
            ps.setInt(4, pedido.getMetodoPagoId());
            ps.setInt(5, pedido.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Pedido WHERE id=?";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
