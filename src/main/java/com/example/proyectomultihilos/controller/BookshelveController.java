package com.example.proyectomultihilos.controller;
import com.example.proyectomultihilos.model.DAO.BookDAO;
import com.example.proyectomultihilos.model.domain.Book;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookshelveController {

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

    private final BookDAO library = new BookDAO();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    private boolean collectingInfo = false;

    public BookshelveController() {
        library.addBook(new Book(123, "El quijote", "Miguel de Cervantes", true));
        library.addBook(new Book(124, "Bajo la misma estrella", "John Green", true));
        library.addBook(new Book(125, "El perfume", "Patrick Süskind", true));
        library.addBook(new Book(126, "Ciencia", "Carl Sagan", true));
        library.addBook(new Book(127, "Orgullo y prejuicio", "Jane Austen", true));
        library.addBook(new Book(128, "Crimen y castigo", "Carl Sagan", true));
        library.addBook(new Book(129, "Cien años de soledad", "Gabriel Garcia Márquez", true));

    }

    @FXML
    void updateInfo(ActionEvent event) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                // Deshabilitar el TextArea mientras se recopila información
                Platform.runLater(() -> txtArea.setDisable(true));

                library.updateBooksInfo();

                // Habilitar el TextArea después de completar la recopilación de información
                Platform.runLater(() -> {
                    txtArea.appendText("Actualización masiva de información completa.\n");
                    txtArea.setDisable(false);
                });

                return null;
            }
        };

        executorService.submit(task);
    }

    @FXML
    void initialize() {
        rec1.setOnMouseClicked(this::showBookInfo);
        rec2.setOnMouseClicked(this::showBookInfo);
        rec3.setOnMouseClicked(this::showBookInfo);
        rec4.setOnMouseClicked(this::showBookInfo);
        rec5.setOnMouseClicked(this::showBookInfo);
        rec6.setOnMouseClicked(this::showBookInfo);
        rec7.setOnMouseClicked(this::showBookInfo);

        btnHistory.setOnAction(this::showHistory);
    }

    @FXML
    private void showBookInfo(MouseEvent event) {
        if (!collectingInfo) {
            Rectangle selectedRectangle = (Rectangle) event.getSource();
            Book selectedBook = getSelectedBook(selectedRectangle);

            if (selectedBook != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información del libro");
                alert.setHeaderText(selectedBook.getName());

                ButtonType prestarButton = new ButtonType("Prestar");
                ButtonType devolverButton = new ButtonType("Devolver");

                alert.getButtonTypes().setAll(prestarButton, devolverButton);
                alert.setContentText("Autor: " + selectedBook.getAuthor() + "\n" +
                        "Disponibilidad: " + (selectedBook.isAvailable() ? "Disponible" : "No disponible"));

                ButtonType result = alert.showAndWait().orElse(null);

                if (result == prestarButton) {
                    prestarLibro(selectedBook);
                } else if (result == devolverButton) {
                    devolverLibro(selectedBook);
                }
            }
        }
    }

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

    private void prestarLibro(Book selectedBook) {
        executorService.submit(() -> {
            if (selectedBook.isAvailable()) {
                library.borrowBook(selectedBook); // Llamar al método borrowBook de BookDAO
                Platform.runLater(() -> txtArea.appendText("Libro prestado: " + selectedBook.getName() + ".\n"));
            } else {
                Platform.runLater(() -> txtArea.appendText("El libro ya está prestado.\n"));
            }
        });
    }
    /*devolver*/
    private void devolverLibro(Book selectedBook) {
        executorService.submit(() -> {
            library.returnBook(selectedBook); // Llamar al método returnBook de BookDAO
            Platform.runLater(() -> txtArea.appendText("Libro devuelto: " + selectedBook.getName() + ".\n"));
        });
    }
    @FXML
    void showHistory(ActionEvent event) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                // Deshabilitar el TextArea mientras se recopila el historial
                Platform.runLater(() -> txtArea.setDisable(true));

                List<String> historyEntries = library.getHistory();
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

        executorService.submit(task);
    }
}

