package jv.conectBD.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase que se encarga de la gestion de la tabla compras
 *
 * @version 0.01
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class ComprasModel extends ConnectionModel {

    private String id_cliente,id_producto,fecha_hora,cantidad;

    /**
     * Metodo que se encarga de resetear la tabla compras
     *
     * @return int 0 si no hay errores, 1 si hay errores
     */
    public int resetTableCompras() {
        int message = 0;

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("TRUNCATE TABLE compras;");
            stmt.executeUpdate();
        } catch (SQLException e) {
            message = 1;
        }

        return message;
    }
}
