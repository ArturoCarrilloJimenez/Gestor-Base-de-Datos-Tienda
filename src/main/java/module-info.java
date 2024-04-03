module org.example.conectarconbasesdedatos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens jv.conectarconbasesdedatos to javafx.fxml;
    exports jv.conectarconbasesdedatos;
    exports jv.conectarconbasesdedatos.controller;
    opens jv.conectarconbasesdedatos.controller to javafx.fxml;
}