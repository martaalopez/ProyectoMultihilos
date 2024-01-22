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

    // Etiqueta para mostrar mensajes de éxito o error
    @FXML
    private Label label;

    // Botón de inicio de sesión
    @FXML
    private Button login;

    // Campo de contraseña
    @FXML
    private PasswordField txtPassword;

    // Campo de nombre de usuario
    @FXML
    private TextField txtUser;

    // Método llamado cuando se presiona el botón de inicio de sesión
    @FXML
    void login(ActionEvent event) throws IOException {
        // Verifica si el nombre de usuario y la contraseña coinciden con los valores esperados
        if (txtUser.getText().equals("admin") && txtPassword.getText().equals("admin")){
            // Muestra un mensaje de éxito si las credenciales son correctas
            label.setText("Correct user and password!");
            label.setTextFill(Color.GREEN);
            // Cambia a la vista del estante de libros después del inicio de sesión exitoso
            App.setRoot("bookshelve");
        } else {
            // Muestra un mensaje de error si las credenciales son incorrectas
            label.setText("Wrong username or password!");
            label.setTextFill(Color.RED);
        }
    }
}
