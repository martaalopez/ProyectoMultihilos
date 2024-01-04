module com.example.proyectomultihilos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyectomultihilos to javafx.fxml;
    exports com.example.proyectomultihilos;
    exports com.example.proyectomultihilos.controller;
    opens com.example.proyectomultihilos.controller to javafx.fxml;
}