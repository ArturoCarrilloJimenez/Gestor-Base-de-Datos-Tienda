package jv.conectBD.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import jv.conectBD.ClassObject.Compra;
import jv.conectBD.ClassObject.Producto;
import jv.conectBD.model.ProductosModel;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Clase que se encarga de la logica de la vista de producto
 *
 * @version 1.0
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class ProductoController implements Initializable {

    @FXML
    private TextField nombre, descripcion, pvp;

    @FXML
    private Label messageInsert;

    @FXML
    private ImageView alertNombre, alertDescripcion, alertPvp;

    @FXML
    private TableView<Producto> tableViewProductos;

    @FXML
    private TableColumn<Producto, String> columnNombre, columnDescripcion;

    @FXML
    private TableColumn<Producto, Double> columnPvp;

    @FXML
    private TableColumn<Producto, Integer> columnId;

    @FXML
    private TableColumn<Producto, Button> columnEdit, columnDelete;

    @FXML
    private TextField InsertBuscar;

    /**
     * Metodo que añade un producto a la base de datos y valida los campos
     *
     */
    @FXML
    public void addProducto() {
        // Se comprueba que los campos no esten vacios
        if (nombre.getText().isEmpty() || descripcion.getText().isEmpty() || pvp.getText().isEmpty()) {
            // Se indica que campos estan vacios
            if (nombre.getText().isEmpty()) {
                alertNombre.opacityProperty().setValue(1);
            } else {
                alertNombre.opacityProperty().setValue(0);
            }

            if (descripcion.getText().isEmpty()) {
                alertDescripcion.opacityProperty().setValue(1);
            } else {
                alertDescripcion.opacityProperty().setValue(0);
            }

            if (pvp.getText().isEmpty()) {
                alertPvp.opacityProperty().setValue(1);
            } else {
                alertPvp.opacityProperty().setValue(0);
            }

            // Se indica que faltan datos
            messageInsert.setText("Faltan datos");
        } else {
            try {
                Double pvpDouble = Double.parseDouble(pvp.getText());
                if (pvpDouble < 0) {
                    alertPvp.opacityProperty().setValue(1);
                    alertNombre.opacityProperty().setValue(0);
                    alertDescripcion.opacityProperty().setValue(0);
                    messageInsert.setText("Precio unitario incorrecto");
                } else {
                    alertPvp.opacityProperty().setValue(0);
                    alertNombre.opacityProperty().setValue(0);
                    alertDescripcion.opacityProperty().setValue(0);

                    pvpDouble = truncate(pvpDouble, 2);

                    int message = ProductosModel.addProducto(nombre.getText(), descripcion.getText(), pvpDouble);

                    if (message == 1) {
                        messageInsert.setText("Algo ha fallado al intentar añadir el producto");
                    } else {
                        messageInsert.setText("El producto " + nombre.getText() + " se a añadido correctamente");
                        initTable();
                    }
                }
            } catch (NumberFormatException e) {
                alertPvp.opacityProperty().setValue(1);
                alertNombre.opacityProperty().setValue(0);
                alertDescripcion.opacityProperty().setValue(0);
                messageInsert.setText("Precio unitario incorrecto, separa los decimales con un punto");
            }
        }
    }

    /**
     * Metodo que trunca un numero decimal a un numero de decimales especificado
     *
     * @param value numero decimal
     * @param decimalPlaces numero de decimales
     * @return double numero decimal truncado
     */
    private double truncate(double value, int decimalPlaces) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_DOWN);
        return bd.doubleValue();
    }

    /**
     * Metodo que inicializa la tabla de productos
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
    }

    /**
     * Metodo que inicializa la tabla de productos
     *
     */
    private void initTable() {
        tableViewProductos.setItems(ProductosModel.getAllProductos());

        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        columnPvp.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPvp()).asObject());

        // Use the btnEditProperty and btnDeleteProperty methods
        columnEdit.setCellValueFactory(cellData -> cellData.getValue().btnEditProperty());
        columnDelete.setCellValueFactory(cellData -> cellData.getValue().btnDeleteProperty());
    }

    /**
     * Metodo que se encarga de buscar un producto en la base de datos
     *
     */
    @FXML
    private void buscar() {
        tableViewProductos.setItems(ProductosModel.buscarProducto(InsertBuscar.getText()));

        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        columnPvp.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPvp()).asObject());

        // Use the btnEditProperty and btnDeleteProperty methods
        columnEdit.setCellValueFactory(cellData -> cellData.getValue().btnEditProperty());
        columnDelete.setCellValueFactory(cellData -> cellData.getValue().btnDeleteProperty());
    }
}
