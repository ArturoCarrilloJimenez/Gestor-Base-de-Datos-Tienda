package jv.conectBD.ClassObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import jv.conectBD.controller.ProductoController;

/**
 * Clase Producto
 *
 * @version 1.0
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class Producto {

    private int id;

    private String nombre,descripcion;
    private double pvp;

    @FXML
    private Button btnEdit,btnDelete;

    /**
     * Constructor de la clase Producto
     *
     * @param id identificador del producto
     * @param nombre nombre del producto
     * @param descripcion descripcion del producto
     * @param pvp precio de venta al publico
     */
    public Producto(int id,String nombre, String descripcion, double pvp) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pvp = pvp;

        // inicializamos los botones
        btnEdit = new Button();
        btnDelete = new Button();

        // Establecemos el estilo de los botones
        btnEdit.setStyle("-fx-cursor: hand;");
        btnDelete.setStyle("-fx-cursor: hand;");

        // Establecemos los iconos de los botones
        btnEdit.setGraphic(new ImageView());
        btnDelete.setGraphic(new ImageView());

        // Canvia el color del boto
        btnEdit.setStyle("-fx-background-color: #00ff00;");
        btnDelete.setStyle("-fx-background-color: #ff0000;");

        // Establecemos los eventos de los botones
        btnEdit.setOnAction(e -> {
            ProductoController.editProducto(this);
        });

        // Establecemos los eventos de los botones
        btnDelete.setOnAction(e -> {
            ProductoController.deleteProducto(this);
        });
    }

    /**
     * Metodo que retorna el nombre del producto
     *
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que retorna la descripcion del producto
     *
     * @return descripcion del producto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo que retorna el precio de venta al publico
     *
     * @return precio de venta al publico
     */
    public double getPvp() {
        return pvp;
    }

    /**
     * Metodo que retorna el identificador del producto
     *
     * @return identificador del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo que retorna el boton de editar
     *
     * @return boton de editar
     */
    public SimpleObjectProperty<Button> btnEditProperty() {
        return new SimpleObjectProperty<>(btnEdit);
    }

    /**
     * Metodo que retorna el boton de eliminar
     *
     * @return boton de eliminar
     */
    public SimpleObjectProperty<Button> btnDeleteProperty() {
        return new SimpleObjectProperty<>(btnDelete);
    }
}
