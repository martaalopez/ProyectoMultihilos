package com.example.proyectomultihilos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Crea una nueva escena con el contenido cargado desde el archivo FXML "mercadoLibre.fxml"
        scene = new Scene(loadFXML("login"), 1440, 900);
        stage.setScene(scene); // Establece la escena en el Stage (ventana principal)
        stage.show(); // Muestra la ventana
        stage.setResizable(false);
    }


    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch();
    }

}
