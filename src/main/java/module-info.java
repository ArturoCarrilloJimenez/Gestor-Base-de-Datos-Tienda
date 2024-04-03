module org.example.conectarconbasesdedatos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens jv.conectBD to javafx.fxml;
    exports jv.conectBD;
    exports jv.conectBD.controller;
    opens jv.conectBD.controller to javafx.fxml;
}