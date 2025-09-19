/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicioRepartidor.negocio;

import ServicioRepartidor.datos.RepartidorDao;
import ServicioRepartidor.datos.entidades.Repartidor;
import java.util.List;

/**
 *
 * @author USER
 */
public class RepartidorN {

    private final RepartidorDao dao = new RepartidorDao();

    public List<Repartidor> listar() throws Exception {
        return dao.listar();
    }

    public boolean crear(Repartidor r) throws Exception {
        return dao.crear(r);
    }

    public Repartidor obtener(int id) throws Exception {
        return dao.obtenerPorId(id);
    }

    public boolean actualizar(Repartidor r) throws Exception {
        return dao.actualizar(r);
    }

    public boolean eliminar(int id) throws Exception {
        return dao.eliminar(id);
    }
}
