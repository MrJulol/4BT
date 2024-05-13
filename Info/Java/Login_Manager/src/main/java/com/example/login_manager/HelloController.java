package com.example.login_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    public Label AccountText;
    @FXML
    protected void onHelloButtonClick() {
        AccountText.setText(Data.getLoggedOnAccount().getUsername());
    }
}