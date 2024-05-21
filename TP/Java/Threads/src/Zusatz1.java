/*
Ziel
Entwickle ein Java-Programm, das ein einfaches, thread-sicheres Logging-System mit
System.out.println() und System.out.flush() implementiert. Das System soll in
der Lage sein, Log-Nachrichten von mehreren Threads synchronisiert auf der Konsole
auszugeben, um Überschneidungen und Verlust von Log-Einträgen zu verhindern.
Anforderungen
● Das Programm soll eine Klasse ThreadSafeLogger enthalten, die eine
synchronisierte Methode log(String message) besitzt.
● Die log-Methode soll jede Nachricht auf der Konsole ausgeben und danach
System.out.flush() aufrufen, um sicherzustellen, dass die Nachricht sofort
angezeigt wird.
● Es sollen mindestens drei Threads erstellt werden, die das
ThreadSafeLogger-Objekt verwenden, um Nachrichten zu loggen.
 */


import java.util.stream.IntStream;

public class Zusatz1 {
    public static void main(String[] args) {
        ThreadSafeLogger logger = new ThreadSafeLogger();
        IntStream.range(0,5).forEach(i -> {new Thread(()->{
            logger.log("Thread x" + i);
        }).start();});
    }
}

class ThreadSafeLogger{
    public synchronized void log(String msg){
        System.out.println(msg);
        System.out.flush();
    }
}