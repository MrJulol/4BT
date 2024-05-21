package org.example.tictactoe;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void log(String message) {
        System.out.println(getCurrentDate() + ": " + message);
    }
    private static String getCurrentDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormatter.format(new Date());
    }
}
