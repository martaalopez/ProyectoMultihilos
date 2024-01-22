package com.example.proyectomultihilos.controller;
import com.example.proyectomultihilos.model.DAO.BookDAO;
import com.example.proyectomultihilos.model.DAO.Chronometer;
import com.example.proyectomultihilos.model.domain.Book;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookshelveController {

    // Declaración de atributos de la interfaz gráfica
    @FXML
    private Rectangle rec1;

    @FXML
    private Rectangle rec2;

    @FXML
    private Rectangle rec3;

    @FXML
    private Rectangle rec4;

    @FXML
    private Rectangle rec5;

    @FXML
    private Rectangle rec6;

    @FXML
    private Rectangle rec7;

    @FXML
    private Button btnBorrow;

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnHistory;

    @FXML
    private TextArea txtArea;

    @FXML
    private Label counterLabel;

    // Instancias de clases para manejar la lógica del programa
    private final BookDAO library = new BookDAO();
    private final ExecutorService executorService = Executors.newFixedThreadPool(7);

    // Flag para controlar la recopilación de información
    private boolean collectingInfo = false;

    private Chronometer chronometer;



    // Constructor donde se agregan libros iniciales a la biblioteca
    public BookshelveController() {
        library.addBook(new Book(123, "El quijote", "Miguel de Cervantes", true));
        library.addBook(new Book(124, "Bajo la misma estrella", "John Green", true));
        library.addBook(new Book(125, "El perfume", "Patrick Süskind", true));
        library.addBook(new Book(126, "Ciencia", "Carl Sagan", true));
        library.addBook(new Book(127, "Orgullo y prejuicio", "Jane Austen", true));
        library.addBook(new Book(128, "Crimen y castigo", "Carl Sagan", true));
        library.addBook(new Book(129, "Cien años de soledad", "Gabriel Garcia Márquez", true));

        chronometer = new Chronometer(counterLabel);
        chronometer.start();
    }

    // Método para actualizar la información de los libros de manera masiva
    @FXML
    void updateInfo(ActionEvent event) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                // Deshabilitar el TextArea mientras se recopila información
                Platform.runLater(() -> txtArea.setDisable(true));

                // Llamada al método de la clase BookDAO para actualizar información
                library.updateBooksInfo();

                // Habilitar el TextArea después de completar la recopilación de información
                Platform.runLater(() -> {
                    txtArea.appendText("Actualización masiva de información completa.\n");
                    txtArea.setDisable(false);
                });

                return null;
            }
        };

        // Se envía la tarea al ExecutorService para su ejecución en un hilo separado
        executorService.submit(task);
    }

    // Método de inicialización al cargar la interfaz gráfica
    @FXML
    void initialize() {
        // Asignación de eventos al hacer clic en los Rectangles
        rec1.setOnMouseClicked(this::showBookInfo);
        rec2.setOnMouseClicked(this::showBookInfo);
        rec3.setOnMouseClicked(this::showBookInfo);
        rec4.setOnMouseClicked(this::showBookInfo);
        rec5.setOnMouseClicked(this::showBookInfo);
        rec6.setOnMouseClicked(this::showBookInfo);
        rec7.setOnMouseClicked(this::showBookInfo);

        // Asignación de evento al hacer clic en el botón de historial
        btnHistory.setOnAction(this::showHistory);

        chronometer = new Chronometer(counterLabel);
    }

    // Método para mostrar información detallada de un libro al hacer clic en su Rectangle correspondiente
    @FXML
    private void showBookInfo(MouseEvent event) {
        if (!collectingInfo) {
            // Obtener el Rectangle seleccionado
            Rectangle selectedRectangle = (Rectangle) event.getSource();
            // Obtener el libro correspondiente al Rectangle
            Book selectedBook = getSelectedBook(selectedRectangle);

            if (selectedBook != null) {
                // Crear un diálogo de información
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información del libro");
                alert.setHeaderText(selectedBook.getName());

                // Botones para prestar y devolver
                ButtonType prestarButton = new ButtonType("Prestar");
                ButtonType devolverButton = new ButtonType("Devolver");

                // Configuración del contenido del diálogo
                alert.getButtonTypes().setAll(prestarButton, devolverButton);
                alert.setContentText("Autor: " + selectedBook.getAuthor() + "\n" +
                        "Disponibilidad: " + (selectedBook.isAvailable() ? "Disponible" : "No disponible"));

                // Mostrar el diálogo y obtener la respuesta del usuario
                ButtonType result = alert.showAndWait().orElse(null);

                // Realizar acciones según la respuesta del usuario
                if (result == prestarButton) {
                    prestarLibro(selectedBook);
                } else if (result == devolverButton) {
                    devolverLibro(selectedBook);
                }
            }
        }
    }

    // Método para obtener el libro correspondiente al Rectangle seleccionado
    private Book getSelectedBook(Rectangle selectedRectangle) {
        if (selectedRectangle == rec1) {
            return library.getBookByIndex(0);
        } else if (selectedRectangle == rec2) {
            return library.getBookByIndex(1);
        } else if (selectedRectangle == rec3) {
            return library.getBookByIndex(2);
        } else if (selectedRectangle == rec4) {
            return library.getBookByIndex(3);
        }
        else if (selectedRectangle == rec5) {
            return library.getBookByIndex(4);
        } else if (selectedRectangle == rec6) {
            return library.getBookByIndex(5);
        } else if (selectedRectangle == rec7) {
            return library.getBookByIndex(6);
        }

        return null;
    }


    private final Map<Integer, Chronometer> chronometers = new HashMap<>();


    @FXML
    private void prestarLibro(Book selectedBook) {
        executorService.submit(() -> {
            if (selectedBook.isAvailable()) {
                // Llamar al método borrowBook de BookDAO para prestar el libro
                library.borrowBook(selectedBook);

                // Crear y empezar un nuevo cronómetro para este libro
                Chronometer newChronometer = new Chronometer(counterLabel);
                chronometers.put(selectedBook.getIsbn(), newChronometer);
                newChronometer.start();

                Platform.runLater(() -> {
                    // Actualizar el TextArea en el hilo de la interfaz gráfica
                    txtArea.appendText("Libro prestado: " + selectedBook.getName() + ".\n");
                });
            } else {
                // Informar al usuario si el libro ya está prestado
                Platform.runLater(() -> txtArea.appendText("El libro ya está prestado.\n"));
            }
        });
    }

    @FXML
    private void devolverLibro(Book selectedBook) {
        executorService.submit(() -> {
            // Detener el cronómetro asociado al libro
            Chronometer chronometer = chronometers.remove(selectedBook.getIsbn());
            if (chronometer != null) {
                chronometer.stop();
            }

            // Llamar al método returnBook de BookDAO para devolver el libro
            library.returnBook(selectedBook);

            // Crear el mensaje
            String mensaje = "Libro devuelto: " + selectedBook.getName();
            if (chronometer != null) {
                mensaje += ". Tiempo prestado: " + chronometer.getElapsedTime();
            }
            mensaje += "\n";

            // Actualizar el TextArea en el hilo de la interfaz gráfica
            String finalMensaje = mensaje;
            Platform.runLater(() -> txtArea.appendText(finalMensaje));
        });
    }




    // Método para mostrar el historial de préstamos y devoluciones
    @FXML
    void showHistory(ActionEvent event) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                // Deshabilitar el TextArea mientras se recopila el historial
                Platform.runLater(() -> txtArea.setDisable(true));

                // Obtener el historial de BookDAO
                List<String> historyEntries = library.getHistory();
                // Mostrar el historial en el TextArea
                Platform.runLater(() -> {
                    txtArea.clear();
                    for (String entry : historyEntries) {
                        txtArea.appendText(entry + "\n");
                    }

                    // Habilitar el TextArea después de completar la recopilación del historial
                    txtArea.setDisable(false);
                });

                return null;
            }
        };

        // Enviar la tarea al ExecutorService para su ejecución en un hilo separado
        executorService.submit(task);
    }
}
