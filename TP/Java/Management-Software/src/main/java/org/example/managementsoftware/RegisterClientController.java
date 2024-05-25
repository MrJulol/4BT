package org.example.managementsoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class RegisterClientController {
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;
    @FXML
    private DatePicker birthDate;
    @FXML
    private PasswordField passwordInit;
    @FXML
    private PasswordField passwordConfirm;

    public void onRegisterButtonClick(ActionEvent actionEvent) {

        if (!Validation.validateName(name.getText())) {
            showAlert("Invalid name! Exists already or is Empty");
            return;
        }
        if (!Validation.validateAddress(address.getText())) {
            showAlert("Invalid address!");
            return;
        }
        if (!Validation.validatePhone(phone.getText())) {
            showAlert("Invalid phone number!");
            return;
        }
        if (!Validation.validateBirthDate(birthDate.getValue())) {
            showAlert("Invalid birth date! Must be only numbers");
            return;
        }
        if (!Validation.validatePassword(passwordInit.getText(), passwordConfirm.getText())) {
            showAlert("Invalid password!");
            return;
        }

        System.out.println("Validation successful");
        addUser();
        clearAllFields();
        SceneManager.getInstance().switchScene(SceneType.ADMIN);

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

    private void clearAllFields() {
        name.clear();
        address.clear();
        phone.clear();
        birthDate.setValue(null);
        passwordInit.clear();
        passwordConfirm.clear();
    }

    public void onReturnButtonClick(ActionEvent actionEvent) {
        SceneManager.getInstance().switchScene(SceneType.ADMIN);
    }

    private void addUser() {
        System.out.println("Adding user");
        String name = this.name.getText();
        String address = this.address.getText();
        String phone = this.phone.getText();
        LocalDate birthDate = this.birthDate.getValue();
        String password = Encrypt.sha256(this.passwordConfirm.getText());
        Account account = new Account(name, address, phone, birthDate, password, MembershipType.YEARLY);
        Database.getDatabase().addAccount(account);

        HBox box = new HBox();
        box.setSpacing(10);
        Button deleteUser = new Button();
        deleteUser.setText("Delete User");
        deleteUser.setOnAction(event -> {
            System.out.println("Delete User");
            Database.getDatabase().removeAccount(account);
            Data.listView.getItems().remove(box);
            Data.clientList.getItems().setAll(Database.getDatabase().getAccountsAsList().stream().map(a -> new HBox(new Label(a.getName()))).collect(Collectors.toSet()));
        });

        Label finalDate = new Label();
        finalDate.setText(account.getMembership().getExpirationDate().toString());

        SplitMenuButton membership = new SplitMenuButton();
        membership.setText(account.getMembership().name());
        MenuItem yearly = new MenuItem("Yearly");
        yearly.setOnAction((event) -> {
            account.changeMembership(MembershipType.YEARLY);
            membership.setText(account.getMembership().name());
            finalDate.setText(account.getMembership().getExpirationDate().toString());
        });

        MenuItem monthly = new MenuItem("Monthly");
        monthly.setOnAction((event -> {
            account.changeMembership(MembershipType.MONTHLY);
            membership.setText(account.getMembership().name());
            finalDate.setText(account.getMembership().getExpirationDate().toString());
        }));

        MenuItem quarterly = new MenuItem("Quarterly");
        quarterly.setOnAction((event -> {
            account.changeMembership(MembershipType.QUARTERLY);
            membership.setText(account.getMembership().name());
            finalDate.setText(account.getMembership().getExpirationDate().toString());
        }));


        membership.getItems().addAll(yearly, monthly, quarterly);
        membership.setMinWidth(120);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        box.getChildren().addAll(new Label(account.getName()), region, finalDate, deleteUser, membership);
        Data.listView.getItems().add(box);
        Data.clientList.getItems().add(new HBox(new Label(account.getName())));
        System.out.println(Database.getDatabase().getAccountsAsList());
    }
}
