package jv.conectBD.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de la logica de la vista
 *
 * @version 0.01
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class IndexController implements Initializable {
    /**
     * Metodo que inicializa la aplicacion y se conecta a la base de datos
     * Si no se puede conectar a la base de datos, muestra un mensaje de error
     * y cierra la aplicacion
     *
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        int messege = jv.conectBD.model.ConnectionModel.connectToDatabase();

        // Si no se pudo conectar a la base de datos
        if (messege == 1) {
            // Mostrar mensaje de error
            alertConnectionError();
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