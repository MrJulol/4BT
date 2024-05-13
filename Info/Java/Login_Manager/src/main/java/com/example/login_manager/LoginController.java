package com.example.login_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void onLoginButtonClick(ActionEvent actionEvent) throws IOException {
        Account account = new Account(Encrypt.sha256(usernameField.getText()), Encrypt.sha256(passwordField.getText()));
        if(Database.getDatabase().checkAccount(account)) {
            Data.setLoggedOnAccount(account);
            Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            StageController.stage.setTitle("You UwU");
            StageController.stage.setScene(scene);
            StageController.stage.setWidth(screen.getWidth() < 1920 ? scene.getWidth() : 1920);
            StageController.stage.setHeight(screen.getHeight() < 1067 ? screen.getHeight() : 1067);
            StageController.stage.setX(0.0);
            StageController.stage.setY(0.0);
            StageController.stage.show();
        }
    }

    public void onRegisterButtonClick(ActionEvent actionEvent) throws IOException {
        RegisterController.RegisterView();
    }
}
