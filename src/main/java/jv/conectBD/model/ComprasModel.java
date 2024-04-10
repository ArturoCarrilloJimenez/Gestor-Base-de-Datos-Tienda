package jv.conectBD.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jv.conectBD.ClassObject.Compra;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que se encarga de la gestion de la tabla compras
 *
 * @version 1.0
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

    /**
     * Metodo que se encarga de obtener todos los productos de la base de datos
     *
     * @return ObservableList<Producto> lista de productos
     */
    public static ObservableList<Compra> getAllCompras() {
        return getProductos("SELECT cliente.id, CONCAT_WS(\" \",cliente.nombre, cliente.apellido1, cliente.apellido2) AS \"nombreApellodosCliente\", producto.id, producto.nombre, compras.cantidad, compras.fecha_hora\n" +
                "FROM producto INNER JOIN compras\n" +
                "\tON producto.id = compras.id_producto\n" +
                "    INNER JOIN cliente\n" +
                "    ON cliente.id = compras.id_cliente;");
    }

    /**
     * Metodo que se encarga de buscar un producto en la base de datos
     *
     * @return ObservableList<Producto> lista de productos encontrados
     */
    private static ObservableList<Compra> getProductos(String sql) {
        ObservableList<Compra> compras = FXCollections.observableArrayList();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id_compra = rs.getInt("cliente.id");
                String nombreCliente = rs.getString("nombreApellodosCliente");
                int id_producto = rs.getInt("producto.id");
                String nombreProducto = rs.getString("producto.nombre");
                int cantidad = rs.getInt("compras.cantidad");
                String fecha_hora = rs.getString("compras.fecha_hora");

                Compra compra = new Compra(id_compra, nombreCliente, id_producto, nombreProducto, cantidad, fecha_hora);
                compras.add(compra);
            }
        } catch (SQLException e) {

        }

        return compras;
    }

    /**
     * Metodo que se encarga de agregar una compra a la base de datos
     *
     * @param id_cliente id del cliente
     * @param id_producto id del producto
     * @param cantidad cantidad de productos
     * @return int 0 si no hay errores, 1 si hay errores
     */
    public static int addCompra(int id_cliente, int id_producto, int cantidad) {
        int message = 0;

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("INSERT INTO compras (id_cliente, id_producto, cantidad) VALUES (?, ?, ?);");
            stmt.setInt(1, id_cliente);
            stmt.setInt(2, id_producto);
            stmt.setInt(3, cantidad);
            stmt.executeUpdate();
        } catch (SQLException e) {
            message = 1;
        }

        return message;
    }

    /**
     * Metodo que se encarga de buscar un cliente en la base de datos
     *
     * @param cliente nombre del cliente
     * @return ObservableList<Producto> lista de productos encontrados
     */
    public static ObservableList<Compra> buscarCliente(String cliente) {
        ObservableList<Compra> compras;

        String[] nombre = cliente.split(" ");

        if (nombre.length == 1) {
            compras = getProductos("SELECT cliente.id, CONCAT_WS(\" \",cliente.nombre, cliente.apellido1, cliente.apellido2) AS \"nombreApellodosCliente\", producto.id, producto.nombre, compras.cantidad, compras.fecha_hora\n" +
                "FROM producto INNER JOIN compras\n" +
                "\tON producto.id = compras.id_producto\n" +
                "    INNER JOIN cliente\n" +
                "    ON cliente.id = compras.id_cliente\n" +
                "WHERE cliente.nombre LIKE \"%" + nombre[0] + "%\";");
        } else if (nombre.length == 2) {
            compras = getProductos("SELECT cliente.id, CONCAT_WS(\" \",cliente.nombre, cliente.apellido1, cliente.apellido2) AS \"nombreApellodosCliente\", producto.id, producto.nombre, compras.cantidad, compras.fecha_hora\n" +
                "FROM producto INNER JOIN compras\n" +
                "\tON producto.id = compras.id_producto\n" +
                "    INNER JOIN cliente\n" +
                "    ON cliente.id = compras.id_cliente\n" +
                "WHERE cliente.nombre LIKE \"%" + nombre[0] + "%\" AND cliente.apellido1 LIKE \"%" + nombre[1] + "%\";");
        } else {
            compras = getProductos("SELECT cliente.id, CONCAT_WS(\" \",cliente.nombre, cliente.apellido1, cliente.apellido2) AS \"nombreApellodosCliente\", producto.id, producto.nombre, compras.cantidad, compras.fecha_hora\n" +
                "FROM producto INNER JOIN compras\n" +
                "\tON producto.id = compras.id_producto\n" +
                "    INNER JOIN cliente\n" +
                "    ON cliente.id = compras.id_cliente\n" +
                "WHERE cliente.nombre LIKE \"%" + nombre[0] + "%\" AND cliente.apellido1 LIKE \"%" + nombre[1] + "%\" AND cliente.apellido2 LIKE \"%" + nombre[2] + "%\";");
        }

        return compras;
    }

    public static int editCompra(int id_cliente_actual, int id_producto_actual, int cantidad, int id_cliente_anterior, int id_producto_anterior) {
        int message = 0;

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("UPDATE compras SET cantidad = ?,id_cliente = ?,id_producto = ? WHERE id_cliente = ? AND id_producto = ?;");
            stmt.setInt(1, cantidad);
            stmt.setInt(2, id_cliente_actual);
            stmt.setInt(3, id_producto_actual);
            stmt.setInt(4, id_cliente_anterior);
            stmt.setInt(5, id_producto_anterior);
            stmt.executeUpdate();
        } catch (SQLException e) {
            message = 1;
        }

        return message;
    }

    /**
     * Metodo que se encarga de eliminar una compra de la base de datos
     *
     */
    public static int deleteCompra(int id_cliente, int id_producto) {
        int message = 0;

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("DELETE FROM compras WHERE id_cliente = ? AND id_producto = ?;");
            stmt.setInt(1, id_cliente);
            stmt.setInt(2, id_producto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            message = 1;
        }

        return message;
    }
}
