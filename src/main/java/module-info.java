module com.example.proyectomultihilos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;


    opens com.example.proyectomultihilos to javafx.fxml;
    exports com.example.proyectomultihilos;
    exports com.example.proyectomultihilos.controller;
    opens com.example.proyectomultihilos.controller to javafx.fxml;
}