import java.io.*;
import java.net.*;

public class MirrorClient {
    public static void main(String[] args) {
        final String SERVER_IP = "localhost";
        final int PORT = 9999;

        try (Socket socket = new Socket(SERVER_IP, PORT)) {
            System.out.println("Connection establised!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);

                String response = null;
                response = in.readLine();
                System.out.println("Answer:  " + response);

                if (message.equalsIgnoreCase("exit")) {
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
