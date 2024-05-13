import java.security.Signature;

public class Transaction {
    private final Account sender;
    private final Account receiver;
    private final int amount;
    private final byte[] signature;

    /**
    Creates the Transaction
    Checks balances, sender == receiver, invalid amount
    signs transaction
    verifies transaction
     **/
    public Transaction(Account sender, Account receiver, int amount) {
        if (sender.getBalance() < amount) {
            System.out.println("Sender : " + sender);
            System.out.println("Receiver : " + receiver);
            System.out.println("Amount : " + amount);
            throw new IllegalArgumentException("Not enough balance: " + sender);
        }
        if(amount<0){
            throw  new IllegalArgumentException("Negative balance " + amount);
        }
        if (sender.equals(receiver)) {
            throw new IllegalArgumentException("Sender is the Same as Receiver");
        }
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        sender.deductBalance(amount);
        receiver.addBalance(amount);
        this.signature = signTransaction(sender, receiver, amount);
        this.verifyTransaction();

    }

    private byte[] signTransaction(Account sender, Account receiver, int amount) {
        try {
            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(sender.getPrivateKey());
            privateSignature.update((sender.getOwnerName() + receiver.getOwnerName() + amount).getBytes());
            return privateSignature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifyTransaction() {
        try {
            Signature publicSignature = Signature.getInstance("SHA256withRSA");
            publicSignature.initVerify(sender.getPublicKey());
            publicSignature.update((sender.getOwnerName() + receiver.getOwnerName() + amount).getBytes());
            return publicSignature.verify(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public int getAmount() {
        return amount;
    }

}
