package org.example.managementsoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.time.LocalDate;

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
        if (Validation.validateName(name.getText()) && Validation.validateAddress(address.getText()) && Validation.validatePhone(phone.getText()) && Validation.validateBirthDate(birthDate.getValue()) && Validation.validatePassword(passwordInit.getText(), passwordConfirm.getText())) {
            System.out.println("Validation successful");
            addUser();
            clearAllFields();
            SceneManager.getInstance().switchScene(SceneType.ADMIN);
        }
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
        });

        SplitMenuButton membership = new SplitMenuButton();
        membership.setText(account.getMembership().name());
        MenuItem yearly = new MenuItem("Yearly");
        yearly.setOnAction((event) -> {
            account.changeMembership(MembershipType.YEARLY);
            membership.setText(account.getMembership().name());
        });

        MenuItem monthly = new MenuItem("Monthly");
        monthly.setOnAction((event -> {
            account.changeMembership(MembershipType.MONTHLY);
            membership.setText(account.getMembership().name());
        }));

        MenuItem quarterly = new MenuItem("Quarterly");
        quarterly.setOnAction((event -> {
            account.changeMembership(MembershipType.QUARTERLY);
            membership.setText(account.getMembership().name());
        }));
        membership.getItems().addAll(yearly, monthly, quarterly);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        box.getChildren().addAll(new Label(account.getName()), region, deleteUser, membership);
        Data.listView.getItems().add(box);
        System.out.println(Database.getDatabase().getAccountsAsList());
    }
}
