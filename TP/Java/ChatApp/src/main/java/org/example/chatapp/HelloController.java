package org.example.chatapp;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HelloController implements Initializable {
    @FXML
    private ListView<String> userListView;
    @FXML
    private ListView<String> chatListView = new ListView<>();
    @FXML
    private TextField userInputTextField;
    @FXML
    private Button userSendButton;

    PrintWriter out;

    private static final int PORT = 9999;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        run();
    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        String message = userInputTextField.getText();
        this.out.println("test");
    }

    public void run() {
        try (Socket socket = new Socket("localhost", PORT)) {
            chatListView.getItems().add("Connected to server!");

            out = new PrintWriter(socket.getOutputStream(), true);
            DATA.socket = socket;
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println(System.getProperty("user.name"));
            System.out.println(System.getProperty("user.name"));

            out.println("TESTING FROM CLIENT 1");

            Thread server = new Thread(() -> {
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String message;
                    System.out.println("DEBUGGIN");
                    System.out.println(socket.isConnected());
                    while ((message = in.readLine()) != null) {
                        System.out.println("i read something");
                        if (message.equals("bye")) {
                        }
                        chatListView.getItems().add(message);
                    }
                } catch (IOException e) {
                    System.out.println("No message received");
                    e.printStackTrace();
                }
            });
            server.start();
            out.println("TESTING FROM CLIENT 2");
            out.println("TESTING FROM CLIENT 3");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}