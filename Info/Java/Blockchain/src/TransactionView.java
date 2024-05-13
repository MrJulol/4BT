public class TransactionView {
    public String displayTransaction(Transaction transaction) {
        return "\tFrom " + transaction.getSender().getOwnerName() +
                " to " + transaction.getReceiver().getOwnerName() +
                " : " + transaction.getAmount() + "\n";
    }
}
