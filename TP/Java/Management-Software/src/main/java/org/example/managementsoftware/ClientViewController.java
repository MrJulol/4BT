package org.example.managementsoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.sql.SQLException;


public class ClientViewController{
    @FXML
    private Label counter;

    @FXML
    private Label name;

    @FXML
    private ListView<String> CheckinListView;
    @FXML
    private Label expDate;


    /**
     * Sets loggedOn User to null
     * return to login-page
     * @param actionEvent
     */
    public void onReturnButtonPressed(ActionEvent actionEvent) {
        Data.loggedOnAccount = null;
        counter.setText(null);
        name.setText(null);
        expDate.setText(null);
        CheckinListView.getItems().clear();
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
        expDate.setText("Expiration Date: "+Data.loggedOnAccount.getMembership().getExpirationDate().toString());
        CheckinListView.getItems().clear();
        Data.loggedOnAccount.getCheckinStatDates().forEach((date)->{
            CheckinListView.getItems().add(date);
        });
    }
}
