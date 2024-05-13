package org.example.todo_list;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.Set;

public class HelloController {
    @FXML
    ListView<HBox> toDoList = new ListView<>();
    @FXML
    TextField toDoInputField = new TextField();

    public void saveToDo(ActionEvent actionEvent) {
        String toDoText = toDoInputField.getText();
        if (toDoText.isEmpty() || toDoText.matches("\\s+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid text");
            alert.showAndWait();
            toDoInputField.clear();
        }else {
            ToDoDatabase.getInstance().addItem(toDoText);
            toDoInputField.clear();
            this.toDoList.setItems(ToDoDatabase.getInstance().getItems());
        }
    }
}