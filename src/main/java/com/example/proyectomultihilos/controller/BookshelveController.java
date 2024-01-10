package com.example.proyectomultihilos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookshelveController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}