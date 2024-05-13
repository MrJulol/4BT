package com.example.login_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Account admin = new Account(Encrypt.sha256("admin"), Encrypt.sha256("123"));
        Database.getDatabase().addAccount(admin);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 270, 130);
        StageController.stage.setTitle("Login");
        StageController.stage.setScene(scene);
        StageController.stage.setResizable(false);
        StageController.stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}