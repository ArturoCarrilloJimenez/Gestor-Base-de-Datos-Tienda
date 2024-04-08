package jv.conectBD.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import jv.conectBD.IndexApplication;
import jv.conectBD.model.ConnectionModel;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de la logica de la vista
 *
 * @version 1.0
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class IndexController {
    @FXML
    private TextField user, password;


    /**
     * Metodo que se encarga de conectar a la base de datos
     */
    @FXML
    public void login() throws IOException {
        if ((user.getText().equals("root")) && (password.getText().isEmpty())) {
            int messege = ConnectionModel.connectToDatabase(user.getText(), password.getText());

            // Si no se pudo conectar a la base de datos
            if (messege == 1) {
                // Mostrar mensaje de error
                alertConnectionError();
            } else {
                IndexApplication.setRoot("cliente-view");
            }
        } else {
            alertEmptyFields();
        }
    }

    /**
     * Metodo que muestra un mensaje de error al no ingresar usuario o contraseña
     */
    private void alertEmptyFields() {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Usuario o contraseña incorrectos");
            alert.setContentText("No a sido posible conectar a la base de datos, por favor verifique los datos ingresados");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            }
        } catch (Exception e) {
        }
    }

    /**
     * Metodo que muestra un mensaje de error al no poder conectarse a la base de datos
     */
    public static void alertConnectionError() {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al conectar a la base de datos");
            alert.setContentText("No se pudo conectar a la base de datos");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            }
        } catch (Exception e) {
            System.exit(0);
        }
    }
}