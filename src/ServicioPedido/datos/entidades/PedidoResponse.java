package ServicioPedido.datos.entidades;

import java.util.List;

public class PedidoResponse {
    private int id;
    private String fecha;
    private double monto;
    private int clienteId;
    private Integer repartidorId;
    private int metodoPagoId;
    private List<DetallePedido> detalles;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public Integer getRepartidorId() { return repartidorId; }
    public void setRepartidorId(Integer repartidorId) { this.repartidorId = repartidorId; }

    public int getMetodoPagoId() { return metodoPagoId; }
    public void setMetodoPagoId(int metodoPagoId) { this.metodoPagoId = metodoPagoId; }

    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
}
