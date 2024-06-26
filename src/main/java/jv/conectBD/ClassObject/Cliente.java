package jv.conectBD.ClassObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jv.conectBD.controller.ClienteController;

/**
 * Clase Cliente
 * Clase que contiene los atributos de un cliente
 * y los metodos get
 *
 * @version 1.0
 * @since 2024
 * @see ClienteController
 * @author Arturo Carrillo Jimenez
 *
 */
public class Cliente {
    private int id;
    private String nombre,apellido1,apellido2,telefono;

    @FXML
    private Button btnEdit,btnDelete;

    /**
     * Constructor de la clase Cliente
     * @param id Identificador del cliente
     * @param nombre Nombre del cliente
     * @param apellido1 Apellido paterno
     * @param apellido2 Apellido materno
     * @param telefono Telefono del cliente
     */
    public Cliente(int id,String nombre, String apellido1, String apellido2, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;

        // Se crean los botones de editar y eliminar
        btnEdit = new Button();
        btnDelete = new Button();

        // Se les asigna un estilo a los botones
        btnEdit.setStyle("-fx-cursor: hand;");
        btnDelete.setStyle("-fx-cursor: hand;");

        // Se crea una imagen para los botones
        Image imageEdit = new Image(getClass().getResource("/jv/conectBD/image/edit.png").toExternalForm());
        Image imageDelete = new Image(getClass().getResource("/jv/conectBD/image/delate.png").toExternalForm());

        ImageView editar = new ImageView(imageEdit);
        ImageView eliminar = new ImageView(imageDelete);

        // Canvia el tamaño de la imagen
        editar.setFitHeight(20);
        editar.setFitWidth(20);
        eliminar.setFitHeight(20);
        eliminar.setFitWidth(20);

        btnEdit.setGraphic(editar);
        btnDelete.setGraphic(eliminar);

        // Se les asigna una clase a los botones
        btnEdit.setOnAction(e -> {
            ClienteController.editCliente(this);
        });

        // Se les asigna una clase a los botones
        btnDelete.setOnAction(e -> {
            ClienteController.deleteCliente(this);
        });
    }

    /**
     * Metodos get
     * @return Regresan el valor de los atributos
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodos get
     * @return Regresan el valor de los atributos
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Metodos get
     * @return Regresan el valor de los atributos
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Metodos get
     * @return Regresan el valor de los atributos
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Metodos get
     * @return Regresan el valor de los atributos
     */
    public int getId() {
        return id;
    }

    /**
     * Metodos get
     * @return Regresan el valor de los atributos
     */
    public SimpleObjectProperty<Button> btnEditProperty() {
        return new SimpleObjectProperty<>(btnEdit);
    }

    /**
     * Metodos get
     * @return Regresan el valor de los atributos
     */
    public SimpleObjectProperty<Button> btnDeleteProperty() {
        return new SimpleObjectProperty<>(btnDelete);
    }
}
