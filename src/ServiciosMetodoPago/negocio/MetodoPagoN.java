/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiciosMetodoPago.negocio;

import ServiciosMetodoPago.datos.MetodoPagoDao;
import ServiciosMetodoPago.datos.entidades.MetodoPago;
import java.util.List;
/**
 *
 * @author USER
 */
public class MetodoPagoN {
    private final MetodoPagoDao metodoPagoDAO = new MetodoPagoDao();

    public List<MetodoPago> listarMetodoPagos() throws Exception {
        return metodoPagoDAO.listar();
    }

    public boolean crearMetodoPago(MetodoPago metodoPago) throws Exception {
        return metodoPagoDAO.crear(metodoPago);
    }

    public MetodoPago obtenerMetodoPago(int id) throws Exception {
        return metodoPagoDAO.obtenerPorId(id);
    }

    public boolean actualizarMetodoPago(MetodoPago metodoPago) throws Exception {
        return metodoPagoDAO.actualizar(metodoPago);
    }

    public boolean eliminarMetodoPago(int id) throws Exception {
        return metodoPagoDAO.eliminar(id);
    }
}

