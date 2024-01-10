package com.example.proyectomultihilos.controller;
import com.example.proyectomultihilos.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button login;

    @FXML
    public void login(ActionEvent event) throws IOException {
        App.setRoot("bookshelve");

    }

}
