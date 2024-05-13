package org.example.memorygame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;

public class Test extends Application {
    private static final int GRID_SIZE = 4;
    private static final int PAIR_DELAY = 1000; // Verzögerung in Millisekunden

    private List<Image> images = new ArrayList<>();
    private List<ImageView> cards = new ArrayList<>();
    private ImageView firstCard;
    private ImageView secondCard;
    private boolean canSelect = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Bilder laden
        loadImages();

        // Karten vorbereiten
        prepareCards();

        // Spieloberfläche erstellen
        GridPane gridPane = new GridPane();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int index = row * GRID_SIZE + col;
                ImageView card = cards.get(index);
                gridPane.add(card, col, row);

                // Ereignishandler hinzufügen
                card.setOnMouseClicked(event -> handleCardClick(card));
            }
        }

        // Szene und Bühne erstellen
        Scene scene = new Scene(gridPane, 300, 300);
        primaryStage.setTitle("Memory-Spiel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadImages() {
        // Hier kannst du Bilder laden, zum Beispiel von Dateien oder Ressourcen
        List<String> urls = ImageDatabase.getInstance().getImages(GRID_SIZE*GRID_SIZE);
        urls.forEach(url -> images.add(new Image(url)));
    }

    private void prepareCards() {
        // Karten erstellen und Bilder zuordnen
        for (int i = 0; i < GRID_SIZE * GRID_SIZE / 2; i++) {
            Image image = images.get(i);
            ImageView card1 = createCard(image);
            ImageView card2 = createCard(image);
            cards.add(card1);
            cards.add(card2);
        }

        // Karten mischen
        Collections.shuffle(cards);
    }

    private ImageView createCard(Image image) {
        ImageView card = new ImageView();
        card.setFitWidth(90);
        card.setFitHeight(90);
        card.setUserData(image);
        card.setImage(image); // Verdecktes Bild setzen
        return card;
    }

    private void handleCardClick(ImageView card) {
        if (!canSelect || card.getImage().getUrl().contains("cover_image.png")) {
            return;
        }

        if (firstCard == null) {
            firstCard = card;
            card.setImage((Image) card.getUserData());
        } else if (secondCard == null && card != firstCard) {
            secondCard = card;
            card.setImage((Image) card.getUserData());
            checkForPair();
        }
    }

    private void checkForPair() {
        canSelect = false;

        if (((Image) firstCard.getUserData()).equals(secondCard.getUserData())) {
            // Paar gefunden, aufgedeckt lassen
            resetSelection();
            if (isGameComplete()) {
                endGame();
            }
        } else {
            // Kein Paar, Karten nach einer kurzen Verzögerung verdecken
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    firstCard.setImage(new Image("file:path_to_cover_image.png"));
                    secondCard.setImage(new Image("file:path_to_cover_image.png"));
                    resetSelection();
                }
            }, PAIR_DELAY);
        }
    }

    private void resetSelection() {
        firstCard = null;
        secondCard = null;
        canSelect = true;
    }

    private boolean isGameComplete() {
        for (ImageView card : cards) {
            if (card.getImage().getUrl().contains("cover_image.png")) {
                return false;
            }
        }
        return true;
    }

    private void endGame() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Spiel beendet");
        alert.setHeaderText(null);
        alert.setContentText("Möchten Sie erneut spielen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            prepareCards();
            resetSelection();
            start(new Stage());
        } else {
            Platform.exit();
        }
    }
}
