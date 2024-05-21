package org.example.tictactoe;

import java.net.Socket;

public class Client implements Runnable{
    public final Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
