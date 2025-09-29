
package ServicioCliente.datos.entidades;

/**
 *
 * @author USER
 */
public class Cliente {
    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private double coordenadaX;
    private double coordenadaY;
    private String fechaRegistro;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public double getCoordenadaX() { return coordenadaX; }
    public void setCoordenadaX(double coordenadaX) { this.coordenadaX = coordenadaX; }

    public double getCoordenadaY() { return coordenadaY; }
    public void setCoordenadaY(double coordenadaY) { this.coordenadaY = coordenadaY; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}