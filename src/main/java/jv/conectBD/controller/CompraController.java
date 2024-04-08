package jv.conectBD.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import jv.conectBD.ClassObject.Compra;
import jv.conectBD.model.ClientesModel;
import jv.conectBD.model.ComprasModel;
import jv.conectBD.model.ProductosModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla la vista de Compras
 *
 * @version 1.0
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class CompraController implements Initializable {

    @FXML
    private ChoiceBox<String> clientes, productos;

    @FXML
    private TextField cantidad;

    @FXML
    private Label messageInsert;

    @FXML
    private ImageView alertCliente, alertProducto, alertCantidad;

    @FXML
    private TextField InsertBuscar;

    @FXML
    private TableView<Compra> tableViewCompras;

    @FXML
    private TableColumn<Compra, String> columnNombreCliente, columnNombreProducto, columnFechaHora;

    @FXML
    private TableColumn<Compra, Integer> columnIdCliente, columnIdProducto, columnCantidad;

    @FXML
    private TableColumn<Compra, Button> columnEdit, columnDelete;

    /**
     * Método que inicializa la vista de Compras
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
    }

    /**
     * Método que inicializa la tabla de Compras y carga los datos de los clientes y productos
     */
    private void initTable() {
        tableViewCompras.setItems(ComprasModel.getAllCompras());

        columnIdCliente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdCliente()).asObject());
        columnNombreCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreApellodosCliente()));
        columnIdProducto.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdProducto()).asObject());
        columnNombreProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreProducto()));
        columnCantidad.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCantidad()).asObject());
        columnFechaHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaHora()));

        // Use the btnEditProperty and btnDeleteProperty methods
        columnEdit.setCellValueFactory(cellData -> cellData.getValue().btnEditProperty());
        columnDelete.setCellValueFactory(cellData -> cellData.getValue().btnDeleteProperty());

        chargeChoiceBox();
    }

    /**
     * Método que carga los datos de los clientes y productos en los ChoiceBox
     */
    private void chargeChoiceBox() {
        String[] clientes = ClientesModel.getClientes();
        String[] productos = ProductosModel.getProductos();

        this.clientes.setItems(FXCollections.observableArrayList(clientes));
        this.productos.setItems(FXCollections.observableArrayList(productos));
    }

    /**
     * Método que agrega una compra y valida los campos
     */
    public void addCompra() {
        if (clientes.getValue() == null || productos.getValue() == null || cantidad.getText().isEmpty()) {
            messageInsert.setText("Todos los campos son obligatorios");

            if (clientes.getValue() == null) alertCliente.opacityProperty().setValue(1);
            else alertCliente.opacityProperty().setValue(0);

            if (productos.getValue() == null) alertProducto.opacityProperty().setValue(1);
            else alertProducto.opacityProperty().setValue(0);

            if (cantidad.getText().isEmpty()) alertCantidad.opacityProperty().setValue(1);
            else alertCantidad.opacityProperty().setValue(0);

        } else {
            try {
                String[] cliente = clientes.getValue().split("-");
                String[] producto = productos.getValue().split("-");
                int cantidad = Integer.parseInt(this.cantidad.getText());

                alertCliente.opacityProperty().setValue(0);
                alertProducto.opacityProperty().setValue(0);
                alertCantidad.opacityProperty().setValue(0);

                int message = ComprasModel.addCompra(Integer.parseInt(cliente[0]), Integer.parseInt(producto[0]), cantidad);

                if (message == 0) {
                    messageInsert.setText("Compra agregada correctamente");
                    initTable();
                } else {
                    messageInsert.setText("Error al agregar la compra");
                }
            } catch (NumberFormatException e) {
                messageInsert.setText("La cantidad debe ser un número entero");
                alertCliente.opacityProperty().setValue(0);
                alertProducto.opacityProperty().setValue(0);
                alertCantidad.opacityProperty().setValue(1);
            }
        }
    }

    /**
     * Método que busca una compra por el nombre del cliente
     */
    public void buscar() {
        tableViewCompras.setItems(ComprasModel.buscarCliente(InsertBuscar.getText()));

        columnIdCliente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdCliente()).asObject());
        columnNombreCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreApellodosCliente()));
        columnIdProducto.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdProducto()).asObject());
        columnNombreProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreProducto()));
        columnCantidad.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCantidad()).asObject());
        columnFechaHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaHora()));

        // Use the btnEditProperty and btnDeleteProperty methods
        columnEdit.setCellValueFactory(cellData -> cellData.getValue().btnEditProperty());
        columnDelete.setCellValueFactory(cellData -> cellData.getValue().btnDeleteProperty());
    }
}
