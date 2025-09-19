/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioCliente.datos;

import ServicioCliente.datos.entidades.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class ClienteDao {

    // Listar con join a MetodoPago
    public List<Cliente> listar() throws Exception {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT c.*, m.id AS metodoPagoId, m.nombre AS metodoPagoNombre "
                + "FROM Cliente c INNER JOIN MetodoPago m ON c.metodoPago_id = m.id";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                c.setDireccion(rs.getString("direccion"));
                c.setCoordenadaX(rs.getDouble("coordenadaX"));
                c.setCoordenadaY(rs.getDouble("coordenadaY"));
                c.setFechaRegistro(rs.getString("fechaRegistro"));
                c.setMetodoPagoId(rs.getInt("metodoPagoId"));
                c.setMetodoPagoNombre(rs.getString("metodoPagoNombre"));
                lista.add(c);
            }
        }
        return lista;
    }

    public boolean crear(Cliente c) throws Exception {
        String sql = "INSERT INTO Cliente(nombre, telefono, email, direccion, coordenadaX, coordenadaY, metodoPago_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getDireccion());
            ps.setDouble(5, c.getCoordenadaX());
            ps.setDouble(6, c.getCoordenadaY());
            ps.setInt(7, c.getMetodoPagoId());
            return ps.executeUpdate() > 0;
        }
    }

    public Cliente obtenerPorId(int id) throws Exception {
        String sql = "SELECT c.*, m.id AS metodoPagoId, m.nombre AS metodoPagoNombre "
                + "FROM Cliente c INNER JOIN MetodoPago m ON c.metodoPago_id = m.id WHERE c.id = ?";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                c.setDireccion(rs.getString("direccion"));
                c.setCoordenadaX(rs.getDouble("coordenadaX"));
                c.setCoordenadaY(rs.getDouble("coordenadaY"));
                c.setFechaRegistro(rs.getString("fechaRegistro"));
                c.setMetodoPagoId(rs.getInt("metodoPagoId"));
                c.setMetodoPagoNombre(rs.getString("metodoPagoNombre"));
                return c;
            }
        }
        return null;
    }

    public boolean actualizar(Cliente c) throws Exception {
        String sql = "UPDATE Cliente SET nombre=?, telefono=?, email=?, direccion=?, coordenadaX=?, coordenadaY=?, metodoPago_id=? WHERE id=?";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getDireccion());
            ps.setDouble(5, c.getCoordenadaX());
            ps.setDouble(6, c.getCoordenadaY());
            ps.setInt(7, c.getMetodoPagoId());
            ps.setInt(8, c.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
