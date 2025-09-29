
package ServicioCliente.datos;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author USER
 */
public class Conexion {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/parcialArqui";
    private static final String USER = "root";
    private static final String PASS = "RonaldBd69003180";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}