package org.example.tictactoe;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Server {
    final static int PORT = 8080;
    final static Set<Socket> clients = new HashSet<>();
    final static HashMap<Socket, PrintWriter> clientsPrinting = new HashMap<>();
    static volatile boolean running = true;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Logger.log("Server started. Waiting for clients...");
            Socket socket1 = serverSocket.accept();
            Logger.log("Client connected: " + socket1.getInetAddress());

            Socket socket2 = serverSocket.accept();
            Logger.log("Client connected: " + socket2.getInetAddress());

            clients.add(socket1);
            clients.add(socket2);

            clientsPrinting.put(socket1, new PrintWriter(socket1.getOutputStream(), true));
            clientsPrinting.put(socket2, new PrintWriter(socket2.getOutputStream(), true));

            Logger.log("...Starting game...");
            clients.forEach(socket -> {clientsPrinting.get(socket).println("...Starting game...");});

            while (running) {

            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

