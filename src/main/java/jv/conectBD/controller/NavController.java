package jv.conectBD.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import jv.conectBD.IndexApplication;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase que se encarga de la logica de la vista del menu de navegacion
 *
 *
 * @version 1.0
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class NavController {
    private jv.conectBD.model.ComprasModel comprasModel = new jv.conectBD.model.ComprasModel();

    /**
     * Metodo que se encarga de resetear la tabla compras
     *
     */
    @FXML
    public void resetTableCompra() {
        AtomicInteger messege = new AtomicInteger();

        // Mostrar mensaje de confirmacion
        try {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Comfirmar borrar datos");
            alert.setHeaderText("¿Esta seguro de borrar los datos de la tabla compras?");
            alert.setContentText("Esta accion no se puede deshacer.");

            ButtonType confirmButton = new ButtonType("Comfirmar", ButtonType.OK.getButtonData());
            ButtonType cancelButton = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());

            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == confirmButton) {
                    messege.set(comprasModel.resetTableCompras());
                }
            });
        } catch (Exception e) {
            messege.set(1);
        }


        if (messege.get() == 1) {
            // Mostrar mensaje de error
            jv.conectBD.controller.IndexController.alertConnectionError();
        }
    }

    /**
     * Metodo que cambia la vista a la vista de clientes
     *
     * @throws IOException Excepcion
     */
    @FXML
    public void viewClient() throws IOException {
        IndexApplication.setRoot("cliente-view");
    }

    /**
     * Metodo que cambia la vista a la vista de productos
     *
     * @throws IOException Excepcion
     */
    @FXML
    public void viewProduct() throws IOException {
        IndexApplication.setRoot("producto-view");
    }

    /**
     * Metodo que cambia la vista a la vista de compras
     *
     * @throws IOException Excepcion
     */
    @FXML
    public void viewCompra() throws IOException {
        IndexApplication.setRoot("compra-view");
    }
}
