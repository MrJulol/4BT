import java.io.*;
import java.net.*;
import java.util.concurrent.locks.*;

public class SwiftServer {
    private static final int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };
    private static final int PORT = 12345;
    private static Socket[] clients = new Socket[2];
    private static PrintWriter[] outStreams = new PrintWriter[2];
    private static BufferedReader[] inStreams = new BufferedReader[2];
    private static char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    private static int currentPlayer = 0;
    private static final Lock lock = new ReentrantLock();
    private static boolean gameActive = false;

    public static void main(String[] args) {
        System.out.println("TicTacToe Server startet auf Port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Player connected");
                int playerIndex = (clients[0] == null) ? 0 : 1;
                clients[playerIndex] = client;
                outStreams[playerIndex] = new PrintWriter(client.getOutputStream(), true);
                inStreams[playerIndex] = new BufferedReader(new InputStreamReader(client.getInputStream()));

                new Thread(new ClientHandler(playerIndex)).start();

                if (clients[0] != null && clients[1] != null) {
                    gameActive = true;
                    sendBoard();
                    outStreams[0].println("Du bist dran!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendBoard() {
        StringBuilder boardString = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            boardString.append(board[i]);
            if ((i + 1) % 3 == 0) {
                boardString.append("\n");
            } else {
                boardString.append("|");
            }
        }
        for (PrintWriter out : outStreams) {
            if (out != null) {
                out.println(boardString.toString());
            }
        }
    }

    private static void sendMessage(int playerIndex, String message) {
        outStreams[playerIndex].println(message);
    }

    private static boolean makeMove(int playerIndex, int move) {
        if (move < 0 || move >= board.length || board[move] != ' ') {
            sendMessage(playerIndex, "Ungültiger Zug. Versuche es erneut.");
            return false;
        }
        board[move] = (playerIndex == 0) ? 'X' : 'O';

        return true;
    }

    private static boolean checkWin() {
        for (int[] condition : winConditions) {
            if (board[condition[0]] != ' ' && board[condition[0]] == board[condition[1]] && board[condition[1]] == board[condition[2]]) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBoardFull() {
        for (char c : board) {
            if (c == ' ') {
                return false;
            }
        }
        return true;
    }

    private static void resetGame() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
        currentPlayer = 0;
    }

    private static class ClientHandler implements Runnable {
        private final int playerIndex;

        ClientHandler(int playerIndex) {
            this.playerIndex = playerIndex;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String input = inStreams[playerIndex].readLine();
                    if (input == null) {
                        System.out.println("Input is null");
                        continue;
                    }
                    System.out.println("Player " + playerIndex + ": " + input);

                    lock.lock();
                    try {
                        if (playerIndex != currentPlayer) {
                            sendMessage(playerIndex, "Du bist nicht dran!");
                        } else {
                            if (makeMove(playerIndex, Integer.parseInt(input))) {
                                sendBoard();
                                if (checkWin()) {
                                    sendMessage(playerIndex, "Du hast gewonnen!");
                                    resetGame();
                                    sendMessage(1 - playerIndex, "Schade Schokolade :(");
                                    gameActive = false;
                                } else if (isBoardFull()) {
                                    sendMessage(playerIndex, "Unentschieden!");
                                    sendMessage(1 - playerIndex, "Unentschieden!");
                                    resetGame();
                                    gameActive = false;
                                } else {
                                    currentPlayer = 1 - currentPlayer;
                                    sendMessage(currentPlayer, "Du bist dran!");
                                }
                            }
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (IOException e) {
                System.err.println("Verbindung zu Spieler " + playerIndex + " verloren.");
            }
        }
    }
}
