package ServicioRegistroCategoria.datos;

import ServicioRegistroCategoria.datos.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import ServicioRegistroCategoria.datos.entidades.Categoria;

/**
 *
 * @author Ronald
 */
public class CategoriaDao {

    public List<Categoria> listar() throws Exception {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
                lista.add(c);
            }
        }
        return lista;
    }

    public boolean crear(Categoria categoria) throws Exception {
        String sql = "INSERT INTO Categoria(nombre, descripcion) VALUES(?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            return ps.executeUpdate() > 0;
        }
    }

    public Categoria obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Categoria WHERE id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
                return c;
            }
        }
        return null;
    }

    public boolean actualizar(Categoria categoria) throws Exception {
        String sql = "UPDATE Categoria SET nombre=?, descripcion=? WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setInt(3, categoria.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Categoria WHERE id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}






/*
**Registrar Eventos de Asistencias**
    
  "Microservicios"
  Angular
   
  Flask 
  Django

  Integrantes: 3
  Tiempo para acabar las fases:

--------------------------------------

  



- Que tiempo para guiar .?
   Mostrar avances

- 

Con grupo nuevo con proyecto nuevo .?

Que nivel de proyecto o en que consiste el proeycto 
Quien Califica
- Que tecnologias voy a guiar.?
   

Cuanto tiempo Dura

Cual voy a guiar Front o Back .?
Grupos de cuantos .?

Voy a calificar .?

Que gana el que acabe el proyecto.?

11 Octubre
*/
