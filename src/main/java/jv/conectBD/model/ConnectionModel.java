package jv.conectBD.model;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que se encarga de la conexion y gestion de la base de datos
 *
 * @version 0.01
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class ConnectionModel {
    protected static java.sql.Connection conn = null;

    /**
     * Metodo que se encarga de la conexion a la base de datos
     *
     */
    public static int connectToDatabase() {
        int message = 0;

        // Librería de MySQL
        String driver = "com.mysql.cj.jdbc.Driver";

        // Ruta de nuestra base de datos (desactivamos el uso de SSL con "?useSSL=false")
        String url = "jdbc:mysql://localhost:3306/ventas?useSSL=false";

        // Conectar a la base de datos
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            message = 1;
        }

        return message;
    }
}
