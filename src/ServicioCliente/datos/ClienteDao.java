
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
    
 // Listar clientes
    public List<Cliente> listar() throws Exception {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
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
                lista.add(c);
            }
        }
        return lista;
    }

    // Crear cliente
    public boolean crear(Cliente c) throws Exception {
        String sql = "INSERT INTO Cliente(nombre, telefono, email, direccion, coordenadaX, coordenadaY) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getDireccion());
            ps.setDouble(5, c.getCoordenadaX());
            ps.setDouble(6, c.getCoordenadaY());
            return ps.executeUpdate() > 0;
        }
    }

    // Obtener cliente por ID
    public Cliente obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
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
                return c;
            }
        }
        return null;
    }

    // Actualizar cliente
    public boolean actualizar(Cliente c) throws Exception {
        String sql = "UPDATE Cliente SET nombre=?, telefono=?, email=?, direccion=?, coordenadaX=?, coordenadaY=? WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getDireccion());
            ps.setDouble(5, c.getCoordenadaX());
            ps.setDouble(6, c.getCoordenadaY());
            ps.setInt(7, c.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // Eliminar cliente
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}