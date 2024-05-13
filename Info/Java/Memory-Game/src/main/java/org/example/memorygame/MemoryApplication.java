package org.example.memorygame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class MemoryApplication extends Application {
    int GRID_SIZE = 4;
    int PAIR_DELAY = 1000;
    List<MyImage> images = new ArrayList();
    private MyImage firstImage;
    private MyImage secondImage;
    private ImageView card1, card2;
    private boolean canSelect = true;

    @Override
    public void start(Stage stage) throws Exception {
        loadImages();
        GridPane gridPane = new GridPane();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int index = row * GRID_SIZE + col;
                ImageView card = new ImageView(images.get(index).hidden);
                card.setFitWidth(90);
                card.setFitHeight(90);
                gridPane.add(card, col, row);

                card.setOnMouseClicked(event -> handleCardClick(images.get(index), card));
            }
        }
        Scene scene = new Scene(gridPane, 300, 300);
        stage.setTitle("Memory-Spiel");
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(90*GRID_SIZE);
        stage.setResizable(false);
        stage.show();

    }

    private void handleCardClick(MyImage image, ImageView card) {
        if (!canSelect) {
            return;
        }

        if (firstImage == null) {
            firstImage = image;
            card.setImage(firstImage.shown);
            card1 = card;
        } else if (secondImage == null) {
            secondImage = image;
            card.setImage(secondImage.shown);
            card2 = card;
            checkForPair();
        }
    }

    private void checkForPair() {
        canSelect = false;

        if (firstImage.urlShown.equals(secondImage.urlShown)) {
            firstImage.isClickable = false;
            secondImage.isClickable = false;
            resetSelection();
            if (isGameComplete()) {
                endGame();
            }
        } else {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    card1.setImage(firstImage.hidden);
                    card2.setImage(secondImage.hidden);
                    resetSelection();
                }
            }, PAIR_DELAY);
        }
    }

    private void resetSelection() {
        firstImage = null;
        secondImage = null;
        canSelect = true;
    }

    private void loadImages() {
        List<String> urls = ImageDatabase.getInstance().getImages(GRID_SIZE * GRID_SIZE);
        System.out.println(urls);
        urls.forEach(url -> {
            try {
                images.add(new MyImage(new Image(new FileInputStream(url)), new Image(new FileInputStream("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/INIT.png")), url, "/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/INIT.png"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        Collections.shuffle(images);
    }

    private boolean isGameComplete() {
        for (MyImage card : images) {
            if (card.isClickable) {
                return false;
            }
        }
        return true;
    }

    private void endGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Spiel beendet");
        alert.setHeaderText(null);
        alert.setContentText("Möchten Sie erneut spielen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            resetSelection();
            try {
                start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Platform.exit();
        }
    }
    static class MyImage{
        Image hidden = null;
        Image shown = null;
        boolean isClickable = false;
        String urlHidden = null;
        String urlShown = null;

        public MyImage(Image shown, Image hidden, String urlShown, String urlHidden) {
            this.hidden = hidden;
            this.shown = shown;
            this.isClickable = true;
            this.urlHidden = urlHidden;
            this.urlShown = urlShown;
        }
    }
}
