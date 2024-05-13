import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockchainController {
    Blockchain blockchain;
    BlockchainView view;

    public BlockchainController(Blockchain blockchain, BlockchainView view) {
        this.blockchain = blockchain;
        this.view = view;
    }

    public void createBlockchain(Account tom, Account max, Account eva) {
        StandardBlockchain(tom, max, eva);

    }

    public void printBlockchain(Account tom, Account max, Account eva){
        System.out.println("\n\nPrinting Blockchain...");
        System.out.println("-".repeat(50));
        new BlockchainView().displayBlockchain(this.blockchain);
        printAccountInfo(tom, max, eva);
    }

    /**
    The provided Test Input
     **/
    private void StandardBlockchain(Account tom, Account max, Account eva) {
        ArrayList<TransactionData> transactionData1 = new ArrayList<>(Arrays.asList(
                new TransactionData(tom, max, 300),
                new TransactionData(tom, eva, 100)));

        ArrayList<TransactionData> transactionData2 = new ArrayList<>(List.of(
                new TransactionData(max, eva, 1000)));

        ArrayList<TransactionData> transactionData3 = new ArrayList<>(Arrays.asList(
                new TransactionData(tom, eva, 500),
                new TransactionData(tom, max, 100),
                new TransactionData(eva, max, 30)));

        TransactionView view = new TransactionView();
        TransactionController controller = new TransactionController(view, new ArrayList<Transaction>());

        controller.createTransaction(transactionData1, this.blockchain);
        controller.createTransaction(transactionData2, this.blockchain);
        controller.createTransaction(transactionData3, this.blockchain);
    }

    /*
    Print the account details to check if transactions were correct. Used for debugging the custom transactions
     */
    private void printAccountInfo(Account tom, Account max, Account eva) {
        System.out.println("-".repeat(50));
        System.out.println("Account Information:");
        System.out.println(max);
        System.out.println(eva);
        System.out.println(tom);

    }
}
