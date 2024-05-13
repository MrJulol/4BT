module org.example.managementsoftware {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens org.example.managementsoftware to javafx.fxml;
    exports org.example.managementsoftware;
}