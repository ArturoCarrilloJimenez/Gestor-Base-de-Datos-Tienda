package jv.conectBD.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import jv.conectBD.ClassObject.Cliente;
import jv.conectBD.IndexApplication;
import jv.conectBD.model.ClientesModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de la logica de la vista de cliente
 *
 * @version 1.0
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
    private TableColumn<Cliente, Button> columnEdit, columnDelete;

    @FXML
    private TextField InsertBuscar;

    /**
     * Metodo que añade un cliente a la base de datos
     * Este comprueba que los campos no esten vacios y que el telefono sea un numero
     *
     */
    @FXML
    private void addCliente() {
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
    private void initTable() {
        tableViewClientes.setItems(ClientesModel.getAllClientes());

        // Se inicializan las columnas
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnApellido1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido1()));
        columnApellido2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido2()));
        columnTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

        // Use the btnEditProperty and btnDeleteProperty methods
        columnEdit.setCellValueFactory(cellData -> cellData.getValue().btnEditProperty());
        columnDelete.setCellValueFactory(cellData -> cellData.getValue().btnDeleteProperty());
    }

    /**
     * Metodo que se encarga de buscar un cliente y mostrarlo en la tabla
     *
     */
    @FXML
    private void buscar() {
        tableViewClientes.setItems(ClientesModel.buscarCliente(InsertBuscar.getText()));

        // Se inicializan las columnas
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnApellido1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido1()));
        columnApellido2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido2()));
        columnTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

        // Use the btnEditProperty and btnDeleteProperty methods
        columnEdit.setCellValueFactory(cellData -> cellData.getValue().btnEditProperty());
        columnDelete.setCellValueFactory(cellData -> cellData.getValue().btnDeleteProperty());
    }

    /**
     * Metodo que se encarga de editar un cliente
     * Este comprueba que los campos no esten vacios y que el telefono sea un numero
     * Este muestra una alerta para confirmar la edicion
     *
     * @throws IOException
     */
    public static void editCliente(Cliente cliente) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Editar cliente");
        alert.setHeaderText("¿Estas seguro de que quieres editar el cliente " + cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2() + "?");

        TextField nombre = new TextField(cliente.getNombre());
        TextField apellido1 = new TextField(cliente.getApellido1());
        TextField apellido2 = new TextField(cliente.getApellido2());
        TextField telefono = new TextField(cliente.getTelefono());

        GridPane grid = new GridPane();
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombre, 1, 0);
        grid.add(new Label("Apellido 1:"), 0, 1);
        grid.add(apellido1, 1, 1);
        grid.add(new Label("Apellido 2:"), 0, 2);
        grid.add(apellido2, 1, 2);
        grid.add(new Label("Telefono:"), 0, 3);
        grid.add(telefono, 1, 3);

        ButtonType buttonTypeYes = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.getDialogPane().setContent(grid);

        alert.showAndWait().ifPresent(type -> {
            if (type == buttonTypeYes) {
                if (nombre.getText().isEmpty() || apellido1.getText().isEmpty() || apellido2.getText().isEmpty() || telefono.getText().isEmpty()) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error al editar el cliente");
                    alert1.setHeaderText("Error al editar el cliente " + cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2());
                    alert1.setContentText("Faltan datos");
                    alert1.showAndWait();
                    return;
                } else if (telefono.getText().length() != 9) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error al editar el cliente");
                    alert1.setHeaderText("Error al editar el cliente " + cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2());
                    alert1.setContentText("Telefono incorrecto, debe tener 9 digitos");
                    alert1.showAndWait();
                    return;
                } else {
                    try {
                        Integer.parseInt(telefono.getText());
                    } catch (NumberFormatException e) {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Error al editar el cliente");
                        alert1.setHeaderText("Error al editar el cliente " + cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2());
                        alert1.setContentText("Telefono incorrecto, debe ser un numero");
                        alert1.showAndWait();
                        return;
                    }
                }

                int message = ClientesModel.editCliente(cliente.getId(), nombre.getText(), apellido1.getText(), apellido2.getText(), telefono.getText());

                if (message == 0) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Cliente editado");
                    alert1.setHeaderText("El cliente " + cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2() + " ha sido editado correctamente");
                    alert1.setContentText("Recarga la pagina para ver los cambios");
                    alert1.showAndWait();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error al editar el cliente");
                    alert1.setHeaderText("Error al editar el cliente " + cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2());
                    alert1.setContentText("Error al editar el cliente");
                    alert1.showAndWait();
                }
            }
        });
    }

    /**
     * Metodo que se encarga de eliminar un cliente
     * Este muestra una alerta para confirmar la eliminacion
     *
     * @param cliente
     */
    public static void deleteCliente(Cliente cliente) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar cliente");
        alert.setHeaderText("¿Estas seguro de que quieres eliminar el cliente " + cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2() + "?");
        alert.setContentText("Esta accion no se puede deshacer");

        ButtonType buttonTypeYes = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(type -> {
            if (type == buttonTypeYes) {
                int message = ClientesModel.deleteCliente(cliente.getId());

                if (message == 0) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Cliente eliminado");
                    alert1.setHeaderText("El cliente " + cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2() + " ha sido eliminado correctamente");
                    alert1.setContentText("Recarga la pagina para ver los cambios");
                    alert1.showAndWait();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error al eliminar el cliente");
                    alert1.setHeaderText("Error al eliminar el cliente " + cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2());
                    alert1.setContentText("Este cliente tiene facturas asociadas, elimina las facturas primero");
                    alert1.showAndWait();
                }
            }
        });
    }
}
