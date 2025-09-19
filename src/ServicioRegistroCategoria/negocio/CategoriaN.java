package ServicioRegistroCategoria.negocio;

import ServicioRegistroCategoria.negocio.*;
import ServicioRegistroCategoria.datos.CategoriaDao;
import ServicioRegistroCategoria.datos.entidades.Categoria;
import java.util.List;

/**
 *
 * @author USER
 */
public class CategoriaN {
    
    private final CategoriaDao categoriaDAO = new CategoriaDao();

    public List<Categoria> listarCategorias() throws Exception {
        return categoriaDAO.listar();
    }

    public boolean crearCategoria(Categoria categoria) throws Exception {
        return categoriaDAO.crear(categoria);
    }

    public Categoria obtenerCategoria(int id) throws Exception {
        return categoriaDAO.obtenerPorId(id);
    }

    public boolean actualizarCategoria(Categoria categoria) throws Exception {
        return categoriaDAO.actualizar(categoria);
    }

    public boolean eliminarCategoria(int id) throws Exception {
        return categoriaDAO.eliminar(id);
    }
}
