module org.example.reminder_list {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.reminder_list to javafx.fxml;
    exports org.example.reminder_list;
}