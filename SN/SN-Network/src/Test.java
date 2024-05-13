import java.io.*;
import java.net.*;
import java.util.*;

public class Test {

    private static final int PORT = 9999;
    private static final Set<ClientThread> clients = new HashSet<>();
    private static final Set<String> wordFilter = new HashSet<>();

    public static void main(String[] args) {

        wordFilter.add("fuck");
        wordFilter.add("shit");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for connections...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                ClientThread clientHandler = new ClientThread(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcastMessage(String message, ClientThread sender) {
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

    private static boolean sendMessageToPerson(String message, ClientThread sender, String receiver) {
        String filteredMessage = filterMessage(message);
        Set<String> nicknames = new HashSet<>();
        clients.forEach(client -> {nicknames.add(client.getNickname());});
        if (!nicknames.contains(receiver)) {
            return false;
        }
        else{
            clients.stream()
                    .filter(client -> client.getNickname().equals(receiver))
                    .forEach(client -> client.sendMessage(sender.getNickname() + ": " + filteredMessage));
        }
        return true;
    }
    private static void sendNicknames(ClientThread sender) {
        StringBuilder sb = new StringBuilder();
        clients.forEach(client -> {sb.append(client.getNickname()).append("\n");});
        sender.sendMessage(sb.toString());
    }

    private static class ClientThread implements Runnable {
        private final Socket clientSocket;
        private String nickname;
        private PrintWriter out;

        public ClientThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public String getNickname() {
            return nickname;
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        @Override
        public void run() {
            Set<String> names = new HashSet<>();
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String nicknameMessage = in.readLine();
                if (nicknameMessage != null && !nicknameMessage.isEmpty()) {
                    clients.forEach(client ->{names.add(client.getNickname());});
                    if(names.contains(nicknameMessage)) {
                        System.out.println("Invalid nickname received. Disconnecting client.");
                        this.sendMessage("bye");
                        clientSocket.close();
                        return;
                    }
                    nickname = nicknameMessage;
                    broadcastMessage(" has joined the chat!", this);
                } else {
                    System.out.println("Invalid nickname received. Disconnecting client.");
                    this.sendMessage("\"Invalid nickname received. Disconnecting.\"");
                    clientSocket.close();
                    return;
                }

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("exit")) {
                        broadcastMessage(" has left the chat!", this);
                        clients.remove(this);
                        clientSocket.close();
                        break;

                    }else if(message.equalsIgnoreCase("/g!")){
                        sendNicknames(this);
                    }
                    else if(message.toLowerCase().startsWith("/r")){
                        String[] words = message.split("!");
                        List<String> listOfWords = Arrays.stream(words).toList();
                        if(!sendMessageToPerson(listOfWords.getLast(), this, listOfWords.get(1))){
                            this.sendMessage("NOT A USER");
                        }
                    }else {
                        broadcastMessage(message, this);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                clients.remove(this);
                try {
                    clientSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
