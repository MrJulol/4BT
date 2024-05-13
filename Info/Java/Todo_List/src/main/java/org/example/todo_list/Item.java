package org.example.todo_list;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.Serializable;


public class Item  implements Serializable {
    private String content;
    private boolean completed;
    private Label contentLB;
    private HBox hbox;
    private Button delete;
    private CheckBox checkBox;

    public Item( String content) {
        this.content = content;
        this.completed = false;
        this.contentLB = new Label();
        contentLB.setText(this.content);
        makeDeleteButton();
        makeCheckbox();
        makeHBox();
    }


    public HBox getBox() {
        return hbox;
    }

    public boolean isCompleted() {
        return completed;
    }

    private void makeDeleteButton(){
        this.delete = new Button("Delete");
        delete.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Item");
            alert.setContentText("Are you sure you want to delete this item?");
            alert.show();
            alert.setOnCloseRequest((DialogEvent event2) -> {
                ButtonType type = alert.getResult();
                if (type == ButtonType.OK) {
                    ToDoDatabase.getInstance().removeItem(this);
                }
            });
        });
    }
    private void makeCheckbox(){
        this.checkBox = new CheckBox();
        checkBox.setOnAction((ActionEvent event) -> {
            this.completed = !this.completed;
            ToDoDatabase.getInstance().updateObservableList();
        } );
    }
    private void makeHBox() {
        HBox buttonsHBox = new HBox(delete, checkBox);
        buttonsHBox.setSpacing(10);
        buttonsHBox.setAlignment(Pos.CENTER_RIGHT);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        this.hbox = new HBox(contentLB, region, buttonsHBox);
    }

    @Override
    public String toString() {
        return STR."Item{content='\{content}\{'\''}, completed=\{completed}\{'}'}";
    }
}
