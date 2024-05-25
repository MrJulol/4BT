package org.example.managementsoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private ListView<String> detailList;

    public void onReturnButtonPressed(ActionEvent actionEvent) {
        SceneManager.getInstance().switchScene(SceneType.ADMIN);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Data.details = detailList;
        Data.detailName = nameLabel;
    }
}
