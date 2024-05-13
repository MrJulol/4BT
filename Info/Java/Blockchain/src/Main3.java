import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        Account tom = new Account("Tom", 1000);
        Account max = new Account("Max", 1000);
        Account eva = new Account("Eva", 1000);
        Blockchain blockchain = new Blockchain(5);
        BlockchainView view = new BlockchainView();
        BlockchainController controller = new BlockchainController(blockchain, view);

        controller.createBlockchain(tom, max, eva);

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDo you want to create a custom transaction? (yes/no)");
        String in = scanner.nextLine();

        if (in.equalsIgnoreCase("yes")) {
            createCustomTransaction(scanner, tom, max, eva, blockchain);
        }
        controller.printBlockchain(tom, max, eva);
    }

    /**
     *Create a Custom Transaction with input from the CLI
     */
    private static void createCustomTransaction(Scanner scanner, Account tom, Account max, Account eva, Blockchain chain) {
        while (true) {
            System.out.println("Enter sender name:");
            String senderName = scanner.nextLine();
            System.out.println("Enter receiver name:");
            String receiverName = scanner.nextLine();
            System.out.println("Enter amount:");
            int amount = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            Account sender = findAccount(senderName, tom, max, eva);
            Account receiver = findAccount(receiverName, tom, max, eva);

            if (sender == null || receiver == null) {
                System.out.println("Invalid sender or receiver name. Please try again.");
                continue;
            }
            if(amount <= 0){
                System.out.println("Invalid amount. Please try again");
                continue;
            }
            TransactionView view = new TransactionView();
            TransactionController controller = new TransactionController(view, new ArrayList<Transaction>());

            controller.createTransaction(new ArrayList<>(List.of(
                    new TransactionData(sender, receiver, amount))),
                    chain);

            System.out.println("\n\nDo you want to create another transaction? (yes/no)");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }

    private static Account findAccount(String name, Account... accounts) {
        for (Account account : accounts) {
            if (account.getOwnerName().equalsIgnoreCase(name)) {
                return account;
            }
        }
        return null;
    }
}
