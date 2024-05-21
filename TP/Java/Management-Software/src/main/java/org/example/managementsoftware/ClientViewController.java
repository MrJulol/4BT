package org.example.managementsoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ClientViewController{
    @FXML
    private Label counter;

    @FXML
    private Label name;

    @FXML
    private ListView<String> CheckinListView;


    /**
     * Sets loggedOn User to null
     * return to login-page
     * @param actionEvent
     */
    public void onReturnButtonPressed(ActionEvent actionEvent) {
        Data.loggedOnAccount = null;
        SceneManager.getInstance().switchScene(SceneType.LOGIN);
    }

    /**
     * Updates the checkinStat Variable in the Account
     * Also updates SQL db
     * @param actionEvent
     */
    public void onCheckinButtonPressed(ActionEvent actionEvent) {
        Data.loggedOnAccount.setCheckinStat(Data.loggedOnAccount.getCheckinStat()+1);
        try{
            Database.getDatabase().updateCheckinStat(Data.loggedOnAccount.getName(), Data.loggedOnAccount.getCheckinStat());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        counter.setText("Number of Logins: "+Data.loggedOnAccount.getCheckinStat());
        name.setText("Welcome Back "+Data.loggedOnAccount.getName());
        CheckinListView.getItems().clear();
        Data.loggedOnAccount.getCheckinStatDates().forEach((date)->{
            CheckinListView.getItems().add(date);
        });
    }
}
