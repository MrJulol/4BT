package org.example.managementsoftware;

import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.util.List;

public class Data {
    public static Account loggedOnAccount;

    public static ListView<HBox> listView;
    public static List<HBox> hBoxList;
    public static ListView<HBox> clientList;

    class Admin{
        private static String username = Encrypt.sha256("admin");
        private static String password = Encrypt.sha256("123");

        public static String getUsername() {
            return username;
        }

        public static String getPassword() {
            return password;
        }
    }
}
