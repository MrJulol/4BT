package org.example.chatapp;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    private static final int PORT = 9999;

    public void main() {
        try (Socket socket = new Socket("localhost", PORT)) {
            System.out.println("Connected to server!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Enter your nickname: ");
            String nickname = new Scanner(System.in).nextLine();
            out.println(nickname);

            new Thread(() -> {
                            try {
                                String message;
                                while ((message = in.readLine()) != null) {
                                    if(message.equals("bye")) {
                            throw new IOException();
                        }
                        System.out.println("\n" + message);
                    }
                } catch (IOException e) {
                    System.out.println("Server disconnected. Exiting...");
                } finally {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }, "Client").start();


            while (true) {
                System.out.println("Do you want to send to a specific Person [y], get a list of users [g] or send to all)");
                String reply = new Scanner(System.in).nextLine();
                if (reply.equals("y")) {
                    System.out.println("Enter the receiver: ");
                    String receiver = new Scanner(System.in).nextLine();
                    System.out.print("Your message: ");
                    String message = new Scanner(System.in).nextLine();
                    out.println("/r!" + receiver + "!" + message);
                }
                else if (reply.equals("g")) {
                    out.println("/g!");
                }
                else{
                    System.out.print("Your message: ");
                    String message = new Scanner(System.in).nextLine();
                    if (message.equalsIgnoreCase("exit")) {
                        break;
                    }
                    out.println(message);
                }


            }

            System.out.println("Disconnecting from server...");
            out.println("exit");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
