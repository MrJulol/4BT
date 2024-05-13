package com.example.login_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;


public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 270, 130);
        StageController.stage.setTitle("Login");
        StageController.stage.setScene(scene);
        StageController.stage.setResizable(false);
        StageController.stage.show();
    }

    public void onRegisterButtonClick(ActionEvent actionEvent) throws IOException {
        String checkUser = InputChecker.checkIfValid(usernameField.getText());
        String checkPasswd = InputChecker.checkIfValid(passwordField.getText());
        if (checkUser != null || checkPasswd != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, checkUser == null ? checkPasswd : checkUser, ButtonType.CLOSE);
            alert.showAndWait();
            RegisterView();
            return;
        }
        if (Database.getDatabase().checkDuplicateUsername(usernameField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User exists already", ButtonType.CLOSE);
            alert.showAndWait();
            RegisterView();
            return;
        }
        Database.getDatabase().addAccount(new Account(Encrypt.sha256(usernameField.getText()), Encrypt.sha256(passwordField.getText())));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 270, 130);
        StageController.stage.setScene(scene);
        StageController.stage.setTitle("Login");
        StageController.stage.setResizable(false);
        StageController.stage.show();

    }

    static void RegisterView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 270, 130);
        StageController.stage.setTitle("Register");
        StageController.stage.setScene(scene);
        StageController.stage.setResizable(false);
        StageController.stage.show();
    }
}
