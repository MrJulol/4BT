package org.example.managementsoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminViewController implements Initializable {

    @FXML
    private Label LoggedOnUser;

    @FXML
    private ListView<HBox> clientList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Data.clientList = clientList;
        clientList.getItems().setAll(Database.getDatabase().getAccountsAsList().stream().map(account -> new HBox(new Label(account.getName()))).collect(Collectors.toSet()));
        LoggedOnUser.setText("Your Clients:");
    }

    public void onRegisterButtonPressed(ActionEvent actionEvent) {
        SceneManager.getInstance().switchScene(SceneType.REGISTER);
    }

    public void onMembershipButtonPressed(ActionEvent actionEvent) {
        SceneManager.getInstance().switchScene(SceneType.MEMBERSHIP);

    }
    public void onReturnButtonPressed(ActionEvent actionEvent) {
        SceneManager.getInstance().switchScene(SceneType.LOGIN);
    }

}
