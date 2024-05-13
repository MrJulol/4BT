package org.example.managementsoftware;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void onLoginButtonClick(ActionEvent actionEvent) {

        if (Validation.isInvalidInput(usernameField.getText())) {
            showAlert("Please enter a username");
            usernameField.clear();
            return;
        }
        if (Validation.isInvalidInput(passwordField.getText())) {
            showAlert("Please enter a password");
            passwordField.clear();
            return;
        }

        if (checkUserForAdmin(usernameField.getText(), passwordField.getText())) {
            SceneManager.getInstance().switchScene(SceneType.ADMIN);
            usernameField.clear();
            passwordField.clear();
        } else if (checkUserForClient(usernameField.getText(), passwordField.getText())) {
            SceneManager.getInstance().switchScene(SceneType.CLIENT);
            usernameField.clear();
            passwordField.clear();
        } else {
            showAlert("Invalid username or password");
            usernameField.clear();
            passwordField.clear();
        }
    }


    /**
     * Yet to be implemented!!
     *
     * @param username
     * @param pass
     */
    private boolean checkUserForClient(String username, String pass) {
        Account tryLogin = Database.getDatabase().findAccountByUsername(username);
        if (tryLogin == null) {
            showAlert("Invalid username or password");
            return false;
        }
        System.out.println(tryLogin.getName());
        System.out.println(tryLogin.getPass());
        System.out.println(username);
        System.out.println(pass);

        if (!tryLogin.getPass().equals(Encrypt.sha256(pass))) {
            showAlert("Invalid username or password");
            return false;
        }
        Data.loggedOnAccount = tryLogin;
        return true;

    }

    /**
     * s
     * Checks if the provided username and password corresponds to the Admin
     *
     * @param username
     * @param password
     * @return true if Admin, false if !Admin
     */
    private boolean checkUserForAdmin(String username, String password) {
        return (Encrypt.sha256(username).equals(Data.Admin.getUsername())
                && Encrypt.sha256(password).equals(Data.Admin.getPassword()));
    }

    /**
     * Shows an alert with the provided Message
     *
     * @param message
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }


}
