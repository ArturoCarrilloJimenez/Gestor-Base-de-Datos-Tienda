package jv.conectBD.ClassObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Clase Compra
 * Esta clase es un objeto que representa una compra
 * Contiene los atributos de una compra y los metodos para obtenerlos
 * Ademas de los botones para editar y eliminar una compra
 *
 * @version 1.0
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class Compra {
    private int idCliente, idProducto, cantidad;
    private String nombreApellodosCliente,nombreProducto,fecha_hora;

    @FXML
    private Button btnEdit,btnDelete;

    /**
     * Constructor de la clase Compra
     * @param idCliente id del cliente
     * @param nombreApellodosCliente nombre y apellidos del cliente
     * @param idProducto id del producto
     * @param nombreProducto nombre del producto
     * @param cantidad cantidad de productos
     * @param fecha_hora fecha y hora de la compra
     */
    public Compra(int idCliente, String nombreApellodosCliente, int idProducto, String nombreProducto, int cantidad, String fecha_hora) {
        this.idCliente = idCliente;
        this.nombreApellodosCliente = nombreApellodosCliente;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.fecha_hora = fecha_hora;

        // Inicializar botones
        btnEdit = new Button();
        btnDelete = new Button();

        // Establecer estilos de los botones
        btnEdit.setStyle("-fx-cursor: hand;");
        btnDelete.setStyle("-fx-cursor: hand;");

        // Establecer iconos de los botones
        btnEdit.setGraphic(new ImageView());
        btnDelete.setGraphic(new ImageView());

        // Establecer eventos de los botones
        btnEdit.setOnAction(e -> {

        });

        // Evento de eliminar
        btnDelete.setOnAction(e -> {

        });
    }

    /**
     * Metodos para obtener los atributos de la clase Compra
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Metodos para obtener los atributos de la clase Compra
     */
    public String getNombreApellodosCliente() {
        return nombreApellodosCliente;
    }

    /**
     * Metodos para obtener los atributos de la clase Compra
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Metodos para obtener los atributos de la clase Compra
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * Metodos para obtener los atributos de la clase Compra
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Metodos para obtener los atributos de la clase Compra
     */
    public String getFechaHora() {
        return fecha_hora;
    }

    /**
     * Metodos para obtener los atributos de la clase Compra
     */
    public SimpleObjectProperty<Button> btnEditProperty() {
        return new SimpleObjectProperty<>(btnEdit);
    }

    /**
     * Metodos para obtener los atributos de la clase Compra
     */
    public SimpleObjectProperty<Button> btnDeleteProperty() {
        return new SimpleObjectProperty<>(btnDelete);
    }
}
