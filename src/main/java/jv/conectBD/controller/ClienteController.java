package jv.conectBD.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import jv.conectBD.ClassObject.Cliente;
import jv.conectBD.model.ClientesModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de la logica de la vista de cliente
 *
 * @version 0.3
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class ClienteController implements Initializable {
    @FXML
    private TextField nombre, apellido1, apellido2, telefono;

    @FXML
    private Label messageInsert;

    @FXML
    private ImageView alertNombre, alertApellido1, alertApellido2, alertTelefono;

    @FXML
    private TableView<Cliente> tableViewClientes;

    @FXML
    private TableColumn<Cliente, String> columnNombre, columnApellido1, columnApellido2, columnTelefono;

    @FXML
    private TableColumn<Cliente, Integer> columnId;

    @FXML
    private TableColumn columnEdit, columnDelete;

    @FXML
    private TextField InsertBuscar;

    /**
     * Metodo que añade un cliente a la base de datos
     *
     */
    @FXML
    public void addCliente() {
        // Se comprueba que los campos no esten vacios
        if (nombre.getText().isEmpty() || apellido1.getText().isEmpty() || apellido2.getText().isEmpty() || telefono.getText().isEmpty()) {
            // Se indica que campos estan vacios
            if (nombre.getText().isEmpty()) {
                alertNombre.opacityProperty().setValue(1);
            } else {
                alertNombre.opacityProperty().setValue(0);
            }

            if (apellido1.getText().isEmpty()) {
                alertApellido1.opacityProperty().setValue(1);
            } else {
                alertApellido1.opacityProperty().setValue(0);
            }

            if (apellido2.getText().isEmpty()) {
                alertApellido2.opacityProperty().setValue(1);
            } else {
                alertApellido2.opacityProperty().setValue(0);
            }

            if (telefono.getText().isEmpty()) {
                alertTelefono.opacityProperty().setValue(1);
            } else {
                alertTelefono.opacityProperty().setValue(0);
            }

            // Se indica que faltan datos
            messageInsert.setText("Faltan datos");
        } else if (telefono.getText().length() != 9) { // Se comprueba que el telefono tenga 9 digitos
            // Se indica que el telefono es incorrecto
            alertTelefono.opacityProperty().setValue(1);
            alertNombre.opacityProperty().setValue(0);
            alertApellido1.opacityProperty().setValue(0);
            alertApellido2.opacityProperty().setValue(0);

            messageInsert.setText("Telefono incorrecto");
        } else { // Se añade el cliente
            try { // Se comprueba que el telefono sea un numero
                Integer.parseInt(telefono.getText());
                // Se ocultan las alertas
                alertTelefono.opacityProperty().setValue(0);
                alertNombre.opacityProperty().setValue(0);
                alertApellido1.opacityProperty().setValue(0);
                alertApellido2.opacityProperty().setValue(0);

                // Se añade el cliente
                int message = ClientesModel.addClinte(nombre.getText(), apellido1.getText(), apellido2.getText(), telefono.getText());

                // Se indica si se ha añadido correctamente
                if (message == 0) {
                    messageInsert.setText("El cliente " + nombre.getText() + " " + apellido1.getText() + " " + apellido2.getText() + " se ha añadido correctamente");
                    initTable();
                } else {
                    messageInsert.setText("Error al añadir el cliente");
                }
            } catch (NumberFormatException e) {
                // Se indica que el telefono es incorrecto
                alertTelefono.opacityProperty().setValue(1);
                alertNombre.opacityProperty().setValue(0);
                alertApellido1.opacityProperty().setValue(0);
                alertApellido2.opacityProperty().setValue(0);

                messageInsert.setText("Telefono incorrecto");
            }
        }
    }

    /**
     * Metodo que se encarga de inicializar la vista
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
    }

    /**
     * Metodo que inicializa la tabla de clientes
     *
     */
    public void initTable() {
        tableViewClientes.setItems(ClientesModel.getAllClientes());

        // Se inicializan las columnas
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnApellido1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido1()));
        columnApellido2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido2()));
        columnTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
    }

    /**
     * Metodo que se encarga de buscar un cliente
     *
     */
    @FXML
    public void buscar() {
        tableViewClientes.setItems(ClientesModel.buscarCliente(InsertBuscar.getText()));

        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnApellido1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido1()));
        columnApellido2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido2()));
        columnTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
    }
}
