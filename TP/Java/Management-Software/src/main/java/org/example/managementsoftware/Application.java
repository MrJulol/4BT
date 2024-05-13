package org.example.managementsoftware;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        StageController.stage = primaryStage;
        Scene scene = SceneManager.getInstance().getScene(SceneType.LOGIN);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
