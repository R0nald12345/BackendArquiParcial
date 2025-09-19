package ServicioRegistroProducto.negocio;

import ServicioRegistroProducto.negocio.*;
import ServicioRegistroProducto.datos.ProductoDao;
import ServicioRegistroProducto.datos.entidades.Producto;
import java.util.List;

/**
 *
 * @author USER
 */
public class ProductoN {

    private final ProductoDao productoDAO = new ProductoDao();

    public List<Producto> listarProductos() throws Exception {
        return productoDAO.listar();
    }

    public boolean crearProducto(Producto producto) throws Exception {
        return productoDAO.crear(producto);
    }

    public Producto obtenerProducto(int id) throws Exception {
        return productoDAO.obtenerPorId(id);
    }

    public boolean actualizarProducto(Producto producto) throws Exception {
        return productoDAO.actualizar(producto);
    }

    public boolean eliminarProducto(int id) throws Exception {
        return productoDAO.eliminar(id);
    }

    public List<Producto> listarProductosPorCategoria(int categoriaId) throws Exception {
        return productoDAO.listarPorCategoria(categoriaId);
    }

}
