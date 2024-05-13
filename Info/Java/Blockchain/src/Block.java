import java.util.ArrayList;
import java.util.List;

public class Block {
    private final long timestamp;
    private final List<Transaction> transactions;
    private String previousHash;
    private String hash;

    /**
     * The list of TransactionData is to keep a record of the transactions in the block to prevent the issue of changing
     *     balances of the accounts. Another solution would have been to only hash the Name of Sender/Receiver and not
     *     the respective balances. I chose to still to hash the whole account.
     */
    private final ArrayList<TransactionData> records;

    private int nonce;

    public Block(List<Transaction> transactions) {
        this.transactions = transactions;
        this.records = new ArrayList<>();
        for(Transaction t : transactions){
            records.add(new TransactionData(new Account(t.getSender().getOwnerName(), t.getSender().getBalance()),
                    new Account(t.getReceiver().getOwnerName(), t.getReceiver().getBalance()),
                    t.getAmount()));
        }
        /*
        Getting the current timestamp from System as long
         */
        this.timestamp = System.currentTimeMillis();
    }

    /*
    Calculate the hash of the Block
    Uses: Hash of previousHash, timestamp, nonce, Records of the transactions
     */
    public String calculateHash() {
        String dataToHash = previousHash + timestamp + nonce + records;
        return Encrypt.sha256(dataToHash);
    }

    /*
    Setter, Getter, ToString
     */
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getPreviousHash() {
        return previousHash;
    }
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        if (this.previousHash.equals("null")){
            return "\n\tGENESIS { " +
                    "\n\t\tHash: '" + hash + '\'' +
                    "\n\t\tTimestamp: " + timestamp +
                    "\n\t\tTransactions: " + transactions.size() +
                    "\n\t\tPreviousHash: '" + null + '\'' +
                    "\n\t\tNonce: " + nonce +
                    "\n\t}";
        }else {
            return "\n\tBlock { " +
                    "\n\t\tHash: '" + hash + '\'' +
                    "\n\t\tTimestamp: " + timestamp +
                    "\n\t\tTransactions: " + transactions.size() +
                    "\n\t\tPreviousHash: '" + previousHash + '\'' +
                    "\n\t\tNonce: " + nonce +
                    "\n\t}";
        }
    }
}
