package org.example.managementsoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    PieChart chart;

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
