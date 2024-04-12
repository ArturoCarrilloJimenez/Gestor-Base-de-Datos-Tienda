package jv.conectBD.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
     * @param url url
     * @param resourceBundle resourceBundle
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

    /**
     * Método que edita una compra
     *
     * @param compra compra a editar
     */
    public static void editCompra(Compra compra) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Editar compra");
        alert.setHeaderText("Editar compra");
        alert.setContentText("¿Estas seguro de que quieres editar la compra?");

        ButtonType buttonTypeYes = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");

        Label label = new Label("Clientes");
        Label label1 = new Label("Productos");
        Label label2 = new Label("Cantidad");


        ChoiceBox<String> clientes = new ChoiceBox<>();
        ChoiceBox<String> productos = new ChoiceBox<>();
        TextField cantidad = new TextField();

        clientes.setItems(FXCollections.observableArrayList(ClientesModel.getClientes()));
        productos.setItems(FXCollections.observableArrayList(ProductosModel.getProductos()));
        cantidad.setText(String.valueOf(compra.getCantidad()));

        GridPane grid = new GridPane();
        grid.add(label, 1, 1);
        grid.add(clientes, 2, 1);
        grid.add(label1, 1, 2);
        grid.add(productos, 2, 2);
        grid.add(label2, 1, 3);
        grid.add(cantidad, 2, 3);

        alert.getDialogPane().setContent(grid);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(type -> {
            if (type == buttonTypeYes) {
                if (clientes.getValue() == null || productos.getValue() == null || cantidad.getText().isEmpty()) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error al editar la compra");
                    alert1.setHeaderText("Todos los campos son obligatorios");
                    alert1.showAndWait();
                } else {
                    String[] cliente = clientes.getValue().split("-");
                    String[] producto = productos.getValue().split("-");

                    try {
                        int cantidadCompra = Integer.parseInt(cantidad.getText());
                        int message = ComprasModel.editCompra(Integer.parseInt(cliente[0]), Integer.parseInt(producto[0]), cantidadCompra,compra.getIdCliente(), compra.getIdProducto(), compra.getFechaHora());

                        if (message == 0) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Compra editada");
                            alert1.setHeaderText("La compra ha sido editada correctamente");
                            alert1.setContentText("Recarga la pagina para ver los cambios");
                            alert1.showAndWait();
                        } else {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Error al editar la compra");
                            alert1.setHeaderText("Error al editar la compra");
                            alert1.showAndWait();
                        }
                    } catch (NumberFormatException e) {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Error al editar la compra");
                        alert1.setHeaderText("La cantidad debe ser un número entero");
                        alert1.showAndWait();

                    }
                }
            }
        });
    }

    /**
     * Método que elimina una compra
     *
     * @param compra compra a eliminar
     */
    public static void deleteCompra(Compra compra) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar compra");
        alert.setHeaderText("¿Estas seguro de que quieres eliminar el la compra?");
        alert.setContentText("Esta accion no se puede deshacer");

        ButtonType buttonTypeYes = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(type -> {
            if (type == buttonTypeYes) {
                    int message = ComprasModel.deleteCompra(compra.getIdCliente(),compra.getIdProducto());

                if (message == 0) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Compra eliminado");
                    alert1.setHeaderText("La compra ha sido eliminado correctamente");
                    alert1.setContentText("Recarga la pagina para ver los cambios");
                    alert1.showAndWait();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error al eliminar el compra");
                    alert1.setHeaderText("Error al eliminar la compra");
                    alert1.showAndWait();
                }
            }
        });
    }
}
