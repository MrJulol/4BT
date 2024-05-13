import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    private static final int PORT = 9999;
    private static final Set<ConnectedClient> clients = new HashSet<>();
    private static final Set<String> wordFilter = new HashSet<>();

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Logger.log("Server started. Waiting for clients...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Logger.log("Client connected: " + clientSocket.getInetAddress());
                //Start thread
                ConnectedClient clientHandler = new ConnectedClient(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("KICKING");
            throw new RuntimeException(e);
        }
    }
    private static void sendMessage(String message, ConnectedClient sender) {

    }
    private static void broadcast(String message, ConnectedClient sender) {

    }

    private static void broadcastMessage(String message, ConnectedClient sender) {
        String filteredMessage = filterMessage(message);

        clients.stream()
                .filter(connClient -> !connClient.equals(sender))
                .forEach(connClient -> connClient.sendMessage(sender.getNickname() + ": " + filteredMessage));
        System.out.println(sender.getNickname() + ": " + filteredMessage);
    }

    private static String filterMessage(String message) {
        String[] words = message.split(" ");
        StringBuilder sb = new StringBuilder();
        Arrays.stream(words).forEach(word -> {
            if(wordFilter.contains(word)) {sb.repeat("*", word.length());}
            else {sb.append(word).append(" ");}
        });
        return sb.toString();
    }


    private static class ConnectedClient implements Runnable {
        private final Socket clientSocket;
        private String nickname;
        private PrintWriter out;

        public ConnectedClient(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        public String getNickname() {return nickname;}

        public void sendMessage(String message) {out.println(message);}

        @Override
        public void run() {
            Set<String> names = new HashSet<>();
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                System.out.println("HERE");

                String nicknameMessage = in.readLine();

                names.add(nicknameMessage);
                System.out.println(nicknameMessage);
                String message;
                System.out.println("TESTING");
                while((message = in.readLine()) != null) {
                    System.out.println("HUH");
                    out.println("IDK ANYMOREEEE11111");
                    if(message.equalsIgnoreCase("exit")) {
                        System.out.println("IDK ANY KMS");
                        clients.remove(this);
                        out.println("IDK ANYMOREEEE");
                        clientSocket.close();
                        break;
                    }
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("GONE");
                e.printStackTrace();
                clients.remove(this);
                try {
                    System.out.println("CLOSED");
                    clientSocket.close();
                }catch (IOException ignored) {
                    ignored.printStackTrace();
                }
            }

        }
    }
}
