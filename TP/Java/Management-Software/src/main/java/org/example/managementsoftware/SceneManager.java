package org.example.managementsoftware;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private HashMap<SceneType, Scene> scenes;
    private static SceneManager instance;

    private SceneManager() {
        scenes = new HashMap<>();
        addDefaultScenes();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }
    public Scene getScene(SceneType sceneType) {
        return scenes.get(sceneType);
    }

    private void addDefaultScenes() {
        try {
            scenes.put(SceneType.LOGIN, new Scene(new FXMLLoader(getClass().getResource("login-view.fxml")).load()));
            scenes.put(SceneType.CLIENT, new Scene(new FXMLLoader(getClass().getResource("client-view.fxml")).load()));
            scenes.put(SceneType.ADMIN, new Scene(new FXMLLoader(getClass().getResource("admin-view.fxml")).load()));
            scenes.put(SceneType.MEMBERSHIP, new Scene(new FXMLLoader(getClass().getResource("membership-view.fxml")).load()));
            scenes.put(SceneType.REGISTER, new Scene(new FXMLLoader(getClass().getResource("registerClient-view.fxml")).load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void switchScene(SceneType sceneType) {
        StageController.stage.setScene(scenes.get(sceneType));
    }
    public void switchScene(Scene scene) {
        StageController.stage.setScene(scene);
    }

}
