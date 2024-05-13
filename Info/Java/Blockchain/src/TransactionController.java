import java.util.ArrayList;

public class TransactionController {
    TransactionView view;
    ArrayList<Transaction> transactions;

    public TransactionController(TransactionView view, ArrayList<Transaction> transactions) {
        this.view = view;
        this.transactions = transactions;
    }

    public void createTransaction(ArrayList<TransactionData> transactionData, Blockchain blockchain){
        transactions = new ArrayList<>();
        try{
            for (TransactionData data : transactionData) {
                transactions.add(new Transaction(data.sender(), data.receiver(), data.amount()));
            }
            blockchain.add(new Block(transactions));
        }
        catch (IllegalArgumentException e){
            System.out.println("Transaction creation failed: " + e.getMessage());
        }
    }
    public static String printTransaction(Transaction transaction){
        return new TransactionView().displayTransaction(transaction);
    }
}
