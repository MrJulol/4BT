module org.example.remindersovernetwork {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.remindersovernetwork to javafx.fxml;
    exports org.example.remindersovernetwork;
}