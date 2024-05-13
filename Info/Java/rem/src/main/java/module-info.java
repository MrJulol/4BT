module com.example.rem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rem to javafx.fxml;
    exports com.example.rem;
}