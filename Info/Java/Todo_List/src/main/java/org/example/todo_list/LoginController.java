package org.example.todo_list;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    Button loginButton;
    @FXML
    PasswordField loginTextField;

    public void onLogin(ActionEvent actionEvent) throws IOException {
        if(UserDatabase.getInstance().isEmpty()){
            UserDatabase.getInstance().addUser(loginTextField.getText());
        }
        if(UserDatabase.getInstance().canLogin(loginTextField.getText())){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = DATA.stage;
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Incorrect password");
            alert.showAndWait();
            loginTextField.clear();
        }
    }


}
