package other;/*
Ziel
Implementiere ein Programm, das die Interaktion zwischen einem Producer-Thread und
einem Consumer-Thread koordiniert. Der Producer soll eine Sequenz von Zahlen
produzieren, und der Consumer soll diese Zahlen verarbeiten, sobald sie verfügbar sind.
Aufgabenstellung
1. Erstelle eine gemeinsame Klasse other.SharedResource, die eine Methode zum
Produzieren von Daten (produce()) und eine Methode zum Konsumieren dieser
Daten (consume()) enthält. Die Methoden müssen synchronisiert sein und wait()
bzw. notify() zur Kommunikation verwenden.
2. Implementiere den Producer-Thread, der wiederholt eine Sequenz von Zahlen
(z.B. die Zahlen 1 bis 5) erzeugt und in other.SharedResource speichert. Nach dem
Speichern der Daten soll er den Consumer-Thread mit notify() benachrichtigen.
3. Implementiere den Consumer-Thread, der auf die Verfügbarkeit der Zahlen wartet
(wait()) und die verfügbaren Zahlen verarbeitet, sobald er benachrichtigt wird.
4. Starte beide Threads und warte auf ihre Beendigung.

Anforderungen
● Die other.SharedResource-Klasse muss synchronized Methoden verwenden, um die
Datenkonsistenz zu gewährleisten.
● Der Consumer-Thread muss warten, bis der Producer Zahlen produziert hat, bevor
er sie verarbeitet.
● Der Producer muss nach der Datenproduktion den Consumer benachrichtigen, damit
dieser die Daten verarbeiten kann.
 */


public class Zusatz2 {

    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        new Thread(()->{ //Producer
            int[] numbers = {1, 2, 3, 4, 5};
            for (int number : numbers) {
                resource.produce(number);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            resource.produce(-1);
        }).start();
        new Thread(()->{ //Consumer
            int value;
            do {
                value = resource.consume();
            } while (value != -1);
        }).start();
    }
}

class SharedResource {
    private Integer number = null;

    public synchronized void produce(int number) {
        while (this.number != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.number = number;
        System.out.println("Produced: " + number);
        notify();
    }

    public synchronized int consume() {
        while (this.number == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        int value = this.number;
        this.number = null;
        System.out.println("Consumed: " + value);
        notify();
        return value;
    }
}

