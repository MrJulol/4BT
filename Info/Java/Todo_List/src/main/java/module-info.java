module org.example.todo_list {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens org.example.todo_list to javafx.fxml;
    exports org.example.todo_list;
}