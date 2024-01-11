package com.example.proyectomultihilos.controller;

import com.example.proyectomultihilos.model.DAO.BookDAO;
import com.example.proyectomultihilos.model.domain.Book;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.sql.Date;
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
    private Button btnBorrow;

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnUpdateInfo;

    @FXML
    private TextArea txtArea;



    private final BookDAO library = new BookDAO();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    Date loanDate = Date.valueOf("2024-01-10");
    Date returnDate = Date.valueOf("2024-01-10");


    public BookshelveController () {
        library.addBook(new Book(123, "El quijote", "Miguel de Cervantes", true, loanDate, returnDate));
    }

    @FXML
    void borrowBook(ActionEvent event) {
        executorService.submit(() -> {
            Book borrowedBook = library.borrowBook();
            if (borrowedBook != null) {
                Platform.runLater(() -> txtArea.appendText("Libro prestado: " + borrowedBook.getName() + ".\n"));
            } else {
                Platform.runLater(() -> txtArea.appendText("No hay libros disponibles para prestar.\n"));
            }
        });
    }

    @FXML
    void returnBook(ActionEvent event) {
        executorService.submit(() -> {
            Book returnedBook = new Book(0,"Libro devuelto","",true,loanDate,returnDate);
            library.returnBook(returnedBook);
            Platform.runLater(() -> txtArea.appendText("Libro devuelto: " + returnedBook.getName() + ".\n"));
        });
    }

    @FXML
    void updateInfo(ActionEvent event) {
        executorService.submit(() -> {
            library.updateBooksInfo();
            Platform.runLater(() -> txtArea.appendText("Actualización masiva de información completa.\n"));
        });
    }
    @FXML
    void initialize() {
        rec1.setOnMouseClicked(this::showBookInfo);
        rec2.setOnMouseClicked(this::showBookInfo);
        rec3.setOnMouseClicked(this::showBookInfo);
        rec4.setOnMouseClicked(this::showBookInfo);
    }

    @FXML
    private void showBookInfo(MouseEvent event) {
        Rectangle selectedRectangle = (Rectangle) event.getSource();
        Book selectedBook = getSelectedBook(selectedRectangle); // Obtener el libro seleccionado
        if (selectedBook != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información del libro");
            alert.setHeaderText(selectedBook.getName());
            alert.setContentText("Autor: " + selectedBook.getAuthor() + "\n" +
                    "Disponibilidad: " + (selectedBook.isAvailable() ? "Disponible" : "No disponible"));
            alert.showAndWait();
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

        return null;
    }








}