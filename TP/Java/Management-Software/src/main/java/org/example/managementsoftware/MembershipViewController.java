package org.example.managementsoftware;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MembershipViewController implements Initializable {
    @FXML
    ListView<HBox> MembershipListView;

    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */

    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<HBox> boxes = new ArrayList<>();
        Data.listView = MembershipListView;
        Data.hBoxList = boxes;

        System.out.println(Database.getDatabase().getAccountsAsList());

        Database.getDatabase().getAccountsAsList().forEach((account -> {
            boxes.add(addAccountHBox(account));
        }));
        MembershipListView.getItems().clear();
        MembershipListView.getItems().addAll(boxes);
    }

    /**
     * @param account
     * @return
     */

    private HBox addAccountHBox(Account account) {
        HBox box = new HBox();
        box.setSpacing(10);

        Button deleteUser = new Button();
        deleteUser.setText("Delete User");
        deleteUser.setOnAction(event -> {
            System.out.println("Delete User");
            Database.getDatabase().removeAccount(account);
            MembershipListView.getItems().remove(box);
            Data.clientList.getItems().clear();
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

        Button details = new Button("Details");
        details.setOnAction((ActionEvent event) -> {
            Data.detailName.setText(Data.loggedOnAccount.getName());
            Data.details.getItems().add(Data.loggedOnAccount.getAddress());
            Data.details.getItems().add(Data.loggedOnAccount.getTelNumber());
            Data.details.getItems().add(Data.loggedOnAccount.getMembership().name());
            Data.details.getItems().add(""+Data.loggedOnAccount.getCheckinStat());
        });

        box.getChildren().addAll(new Label(account.getName()), region, finalDate, deleteUser, membership);
        return box;
    }

    public void onReturnButtonClicked(ActionEvent actionEvent) {
        SceneManager.getInstance().switchScene(SceneType.ADMIN);
    }
}
