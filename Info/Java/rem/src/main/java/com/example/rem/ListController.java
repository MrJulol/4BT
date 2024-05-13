package com.example.rem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ListController  implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionField;

    @FXML
    private ListView<MyEvent> eventList;

    @FXML
    private void initialize() {
        ObservableList<MyEvent> list = FXCollections.observableArrayList();
        eventList.setItems(list);
        addButton.setOnAction(event -> addEvent());
    }

    private void addEvent() {
        LocalDate selectedDate = datePicker.getValue();
        String description = descriptionField.getText();
        if(description == null || description.isEmpty() || description.matches("\\s+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a description");
            alert.showAndWait();
            descriptionField.clear();
        }
        if (selectedDate.isAfter(LocalDate.now()) || selectedDate.equals(LocalDate.now())) {
            MyEvent newEvent = new MyEvent(selectedDate, description);
            ObservableList<MyEvent> items = eventList.getItems();
            items.add(newEvent);
            eventList.setItems(items);
            datePicker.setValue(LocalDate.now());
            descriptionField.clear();
        } else {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            assert description != null;
            if (description.length() <= 3) {
                alert.setContentText("Description should have more than 3 characters.");
            } else {
                alert.setContentText("Error adding event.");
            }
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
    }

    public static class MyEvent {
        private LocalDate date;
        private String description;

        public MyEvent(LocalDate date, String description) {
            this.date = date;
            this.description = description;
        }

        @Override
        public String toString() {
            return date.toString() + " | " + description;
        }
    }
}
