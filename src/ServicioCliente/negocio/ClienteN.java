/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioCliente.negocio;

import ServicioCliente.datos.ClienteDao;
import ServicioCliente.datos.entidades.Cliente;
import java.util.List;

/**
 *
 * @author USER
 */
public class ClienteN {

   private final ClienteDao dao = new ClienteDao();

    public List<Cliente> listarClientes() throws Exception {
        return dao.listar();
    }
    public boolean crearCliente(Cliente c) throws Exception {
        return dao.crear(c);
    }
    public Cliente obtenerCliente(int id) throws Exception {
        return dao.obtenerPorId(id);
    }
    public boolean actualizarCliente(Cliente c) throws Exception {
        return dao.actualizar(c);
    }
    public boolean eliminarCliente(int id) throws Exception {
        return dao.eliminar(id);
    }
}

