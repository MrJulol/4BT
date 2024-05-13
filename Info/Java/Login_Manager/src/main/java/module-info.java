module com.example.login_manager {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.login_manager to javafx.fxml;
    exports com.example.login_manager;
}