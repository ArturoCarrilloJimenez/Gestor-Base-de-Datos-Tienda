package jv.conectBD;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 * Clase que inicia la aplicacion
 *
 * @version 1.0
 * @since 2024
 * @author Arturo Carrillo Jimenez
 */
public class IndexApplication extends Application {

    private static Scene scene;

    /**
     * Metodo que inicia la aplicacion
     *
     * @param stage Stage
     * @throws IOException Excepcion
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("index-view"), 800, 550);
        stage.setTitle("Gestor de la Base de Datos Tienda");
        stage.setScene(scene);
        stage.maxWidthProperty().setValue(800);
        stage.maxHeightProperty().setValue(588);
        stage.minWidthProperty().setValue(800);
        stage.minHeightProperty().setValue(588);
        stage.show();
    }

    /**
     * Metodo que cambia la vista
     *
     * @param fxml String
     * @throws IOException Excepcion
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Metodo que carga el archivo FXML
     *
     * @param fxml String
     * @return Parent
     * @throws IOException Excepcion
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Metodo que inicia la aplicacion
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        launch();
    }
}