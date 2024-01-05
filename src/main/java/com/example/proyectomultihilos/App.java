package com.example.proyectomultihilos;

import com.example.proyectomultihilos.model.DAO.BookDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;

    private static BookDAO bookDAO;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("hello-view"), 540, 500);
        stage.setScene(scene);
        stage.show();
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
        // Inicializar el objeto BookDAO
        bookDAO = new BookDAO();

        // Iniciar la aplicación JavaFX
        launch();
    }

    // Método para tomar un libro desde la interfaz gráfica
    public static void tomarLibroDesdeInterfaz() {
        // Crear un hilo para tomar un libro
        Thread tomador = new Thread(() -> {
            bookDAO.tomarLibro();
        });
        tomador.start();
    }

    // Método para devolver un libro desde la interfaz gráfica
    public static void devolverLibroDesdeInterfaz() {
        // Crear un hilo para devolver un libro
        Thread devolvedor = new Thread(() -> {
            bookDAO.devolverLibro();
        });
        devolvedor.start();
    }

}
