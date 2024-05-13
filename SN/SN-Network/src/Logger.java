import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Logger INSTANCE;
    private Logger() {
    }
    public static Logger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Logger();
        }
        return INSTANCE;
    }
    public static void log(String message) {
        System.out.println(getCurrentDate() + ": " + message);
    }
    private static String getCurrentDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormatter.format(new Date());
    }
}
