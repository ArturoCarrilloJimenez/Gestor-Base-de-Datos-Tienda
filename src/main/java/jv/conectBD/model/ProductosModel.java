package jv.conectBD.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jv.conectBD.ClassObject.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que se encarga de la logica de los productos
 *
 * @version 1.0
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class ProductosModel extends ConnectionModel {

    /**
     * Metodo que se encarga de añadir un producto a la tabla producto
     *
     * @return int 0 si no hay errores, 1 si hay errores
     */
    public static int addProducto(String nombre, String descripcion, double pvp) {
        int message = 0;

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("INSERT INTO producto (nombre,descripcion,pvp) VALUES (?,?,?)");
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDouble(3, pvp);
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
     * Metodo que se encarga de obtener todos los productos de la base de datos
     *
     * @return ObservableList<Producto> lista de productos
     */
    public static ObservableList<Producto> getAllProductos() {
        return getProductos("SELECT * FROM producto");
    }

    /**
     * Metodo que se encarga de buscar un producto en la base de datos
     *
     * @return ObservableList<Producto> lista de productos encontrados
     */
    private static ObservableList<Producto> getProductos(String sql) {
        ObservableList<Producto> productos = FXCollections.observableArrayList();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double pvp = rs.getDouble("pvp");

                Producto producto = new Producto(id,nombre, descripcion, pvp);
                productos.add(producto);
            }
        } catch (SQLException e) {

        }

        return productos;
    }

    /**
     * Metodo que se encarga de buscar un producto en la base de datos
     *
     * @return ObservableList<Producto> lista de productos encontrados
     */
    public static ObservableList<Producto> buscarProducto(String nombre) {
        try {
            int id = Integer.parseInt(nombre);
            return getProductos("SELECT * FROM producto WHERE id = " + id);
        } catch (NumberFormatException e) {
            return getProductos("SELECT * FROM producto WHERE nombre LIKE '%" + nombre + "%'");
        }
    }

    /**
     * Metodo que se encarga de mondar los productos en un array
     *
     * @return int 0 si no hay errores, 1 si hay errores
     */
    public static String[] getProductos() {
        ObservableList<Producto> productos = getAllProductos();
        String[] productosArray = new String[productos.size()];
        for (int i = 0; i < productos.size(); i++) {
            productosArray[i] = (productos.get(i).getId() + "-" + productos.get(i).getNombre());
        }
        return productosArray;
    }

    /**
     * Metodo que se encarga de editar un producto en la base de datos
     *
     * @return int 0 si no hay errores, 1 si hay errores
     */
    public static int editProducto(int id, String nombre, String descripcion, double pvp) {
        int message = 0;

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("UPDATE producto SET nombre = ?, descripcion = ?, pvp = ? WHERE id = ?");
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDouble(3, pvp);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            message = 1;
        }

        return message;
    }

    /**
     * Metodo que se encarga de eliminar un producto en la base de datos
     *
     * @return int 0 si no hay errores, 1 si hay errores
     */
    public static int deleteProducto(int id) {
        int message = 0;

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("DELETE FROM producto WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            message = 1;
        }

        return message;
    }
}
