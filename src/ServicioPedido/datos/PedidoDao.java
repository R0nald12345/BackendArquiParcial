/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioPedido.datos;

import ServicioPedido.datos.entidades.Pedido;
import ServicioPedido.datos.entidades.DetallePedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {

    public boolean crear(Pedido pedido) throws Exception {
        String sqlPedido = "INSERT INTO Pedido(monto, cliente_id, repartidor_id) VALUES(?, ?, ?)";
        String sqlDetalle = "INSERT INTO detallePedido(pedido_id, producto_id, cantidad, precio) VALUES(?, ?, ?, ?)";
        Connection con = null;

        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false);

            // Insertar pedido
            PreparedStatement ps = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, pedido.getMonto());
            ps.setInt(2, pedido.getClienteId());
            ps.setInt(3, pedido.getRepartidorId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int pedidoId = rs.getInt(1);

                // Insertar detalles
                for (DetallePedido dp : pedido.getDetalles()) {
                    PreparedStatement psd = con.prepareStatement(sqlDetalle);
                    psd.setInt(1, pedidoId);
                    psd.setInt(2, dp.getProductoId());
                    psd.setInt(3, dp.getCantidad());
                    psd.setDouble(4, dp.getPrecio());
                    psd.executeUpdate();
                }
            }
            con.commit();
            return true;
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public Pedido obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Pedido WHERE id = ?";
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

                // detalles
                DetallePedidoDao dpDao = new DetallePedidoDao();
                p.setDetalles(dpDao.listarPorPedido(id));

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
                lista.add(p);
            }
        }
        return lista;
    }

    public boolean actualizar(Pedido pedido) throws Exception {
        String sql = "UPDATE Pedido SET monto=?, cliente_id=?, repartidor_id=? WHERE id=?";
        Connection con = null;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false);

            // Actualizar cabecera
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, pedido.getMonto());
            ps.setInt(2, pedido.getClienteId());
            ps.setInt(3, pedido.getRepartidorId());
            ps.setInt(4, pedido.getId());
            ps.executeUpdate();

            // Eliminar detalles previos
            PreparedStatement psDel = con.prepareStatement("DELETE FROM detallePedido WHERE pedido_id=?");
            psDel.setInt(1, pedido.getId());
            psDel.executeUpdate();

            // Insertar nuevos detalles
            String sqlDetalle = "INSERT INTO detallePedido(pedido_id, producto_id, cantidad, precio) VALUES(?, ?, ?, ?)";
            for (DetallePedido dp : pedido.getDetalles()) {
                PreparedStatement psd = con.prepareStatement(sqlDetalle);
                psd.setInt(1, pedido.getId());
                psd.setInt(2, dp.getProductoId());
                psd.setInt(3, dp.getCantidad());
                psd.setDouble(4, dp.getPrecio());
                psd.executeUpdate();
            }

            con.commit();
            return true;
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
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
