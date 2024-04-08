package jv.conectBD.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jv.conectBD.ClassObject.Cliente;
import jv.conectBD.ClassObject.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que se encarga de realizar las operaciones de la tabla cliente
 *
 * @version 1.0
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class ClientesModel extends ConnectionModel {

    /**
     * Metodo que se encarga de añadir un cliente a la tabla cliente
     *
     * @return int 0 si no hay errores, 1 si hay errores
     */
    public static int addClinte(String nombre, String apellido1, String apellido2, String telefono) {
        int message = 0;

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("INSERT INTO cliente (nombre,apellido1,apellido2,telefono) VALUES (?,?,?,?)");
            stmt.setString(1, nombre);
            stmt.setString(2, apellido1);
            stmt.setString(3, apellido2);
            stmt.setString(4, telefono);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                message = 0; // success
            } else {
                message = 1; // failure
            }
        } catch (SQLException e) {
            message = 1;
        }

        return message;
    }

    /**
     * Metodo que se encarga de obtener todos los clientes de la base de datos
     *
     * @return ObservableList<Cliente> lista de clientes
     */
    public static ObservableList<Cliente> getAllClientes() {
        return getCliente("SELECT * FROM cliente");
    }

    /**
     * Metodo que se encarga de buscar un cliente en la base de datos
     *
     * @return ObservableList<Cliente> lista de clientes encontrados
     */
    private static ObservableList<Cliente> getCliente(String sql) {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                String telefono = rs.getString("telefono");

                Cliente cliente = new Cliente(id, nombre, apellido1, apellido2, telefono);
                clientes.add(cliente);
            }
        } catch (SQLException e) {

        }
        return clientes;
    }

    /**
     * Metodo que se encarga de buscar un cliente en la base de datos
     *
     * @return ObservableList<Cliente> lista de clientes encontrados
     */
    public static ObservableList<Cliente> buscarCliente(String nombre) {
        try {
            int id = Integer.parseInt(nombre);
            return getCliente("SELECT * FROM cliente WHERE id = " + id);
        } catch (NumberFormatException e) {
            String[] nombres = nombre.split(" ");

            if (nombres.length == 1) {
                return getCliente("SELECT * FROM cliente WHERE nombre LIKE '%" + nombres[0] + "%'");
            } else if (nombres.length == 2) {
                return getCliente("SELECT * FROM cliente WHERE nombre LIKE '%" + nombres[0] + "%' AND apellido1 LIKE '%" + nombres[1] + "%'");
            } else {
                return getCliente("SELECT * FROM cliente WHERE nombre LIKE '%" + nombres[0] + "%' AND apellido1 LIKE '%" + nombres[1] + "%' AND apellido2 LIKE '%" + nombres[2] + "%'");
            }
        }
    }

    /**
     * Metodo que se encarga de obtener los nombres de los clientes
     *
     * @return String[] lista de nombres de los clientes
     */
    public static String[] getClientes() {
        ObservableList<Cliente> clientes = getAllClientes();
        String[] nombres = new String[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            nombres[i] = (clientes.get(i).getId() + "-" + clientes.get(i).getNombre() + " " + clientes.get(i).getApellido1() + " " + clientes.get(i).getApellido2());
        }
        return nombres;
    }

    /**
     * Metodo que se encarga de eliminar un cliente de la base de datos
     *
     * @return int 0 si no hay errores, 1 si hay errores
     */
    public static int deleteCliente(int id) {
        int message = 0;

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("DELETE FROM cliente WHERE id = ?");
            stmt.setInt(1, id);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                message = 0; // success
            } else {
                message = 1; // failure
            }
        } catch (SQLException e) {
            message = 1;
        }

        return message;
    }

    /**
     * Metodo que se encarga de editar un cliente de la base de datos
     *
     * @return int 0 si no hay errores, 1 si hay errores
     */
    public static int editCliente(int id, String text, String text1, String text2, String text3) {
        int message = 0;

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("UPDATE cliente SET nombre = ?, apellido1 = ?, apellido2 = ?, telefono = ? WHERE id = ?");
            stmt.setString(1, text);
            stmt.setString(2, text1);
            stmt.setString(3, text2);
            stmt.setString(4, text3);
            stmt.setInt(5, id);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                message = 0; // success
            } else {
                message = 1; // failure
            }
        } catch (SQLException e) {
            message = 1;
        }

        return message;
    }
}
