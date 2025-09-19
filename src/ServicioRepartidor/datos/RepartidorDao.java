/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioRepartidor.datos;

import ServicioRepartidor.datos.entidades.Repartidor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author USER
 */
public class RepartidorDao {
    
   public List<Repartidor> listar() throws Exception {
        List<Repartidor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Repartidor";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Repartidor r = new Repartidor();
                r.setId(rs.getInt("id"));
                r.setNombre(rs.getString("nombre"));
                r.setCi(rs.getString("ci"));
                r.setPlaca(rs.getString("placa"));
                r.setCelular(rs.getString("celular")); 
                lista.add(r);
            }
        }
        return lista;
    }
    
    public boolean crear(Repartidor r) throws Exception {
        // MODIFICAR LA CONSULTA SQL PARA INCLUIR CELULAR
        String sql = "INSERT INTO Repartidor(nombre, ci, placa, celular) VALUES(?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getCi());
            ps.setString(3, r.getPlaca());
            ps.setString(4, r.getCelular()); 
            return ps.executeUpdate() > 0;
        }
    }
    
    public Repartidor obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Repartidor WHERE id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Repartidor r = new Repartidor();
                r.setId(rs.getInt("id"));
                r.setNombre(rs.getString("nombre"));
                r.setCi(rs.getString("ci"));
                r.setPlaca(rs.getString("placa"));
                r.setCelular(rs.getString("celular")); 
                return r;
            }
        }
        return null;
    }
    
    public boolean actualizar(Repartidor r) throws Exception {
        // MODIFICAR LA CONSULTA SQL PARA INCLUIR CELULAR
        String sql = "UPDATE Repartidor SET nombre=?, ci=?, placa=?, celular=? WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getCi());
            ps.setString(3, r.getPlaca());
            ps.setString(4, r.getCelular()); 
            ps.setInt(5, r.getId()); 
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Repartidor WHERE id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}