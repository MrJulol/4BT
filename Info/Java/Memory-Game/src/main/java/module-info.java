module org.example.memorygame {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.memorygame to javafx.fxml;
    exports org.example.memorygame;
}