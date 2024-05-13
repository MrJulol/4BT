import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        final int PORT = 9999;
        int ID = 0;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for connections...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, ++ID);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final int ID;

    public ClientHandler(Socket clientSocket, int ID) {
        this.clientSocket = clientSocket;
        this.ID = ID;
    }

    @Override
    public void run() {
        System.out.println("Client " + this.ID + " connected!");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String message;
            while ((message = in.readLine()) != null) {
                if (!message.equalsIgnoreCase("exit")) {
                    System.out.println("Received Message from Client" + this.ID + " : " + message);
                    out.println(message);
                } else {
                    out.println("Bye Bye!");
                    System.out.println("Client " + this.ID + " disconnected!");
                    clientSocket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
