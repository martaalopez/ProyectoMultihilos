package com.example.proyectomultihilos.controller;

import com.example.proyectomultihilos.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label label;

    @FXML
    private Button login;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @FXML
    void login(ActionEvent event) throws IOException {
        if (txtUser.getText().equals("admin") && txtPassword.getText().equals("admin")){
            label.setText("Correct user and password!");
            label.setTextFill(Color.GREEN);
            App.setRoot("bookshelve");
        }else {
            label.setText("Wrong username or password!");
            label.setTextFill(Color.RED);
        }
    }


}
