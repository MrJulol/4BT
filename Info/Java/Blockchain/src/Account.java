import java.security.*;

public class Account {
    private final String ownerName;
    private int balance;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public Account(String ownerName, int balance) {
        this.ownerName = ownerName;
        this.balance = balance;
        generateKeyPair();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getBalance() {
        return balance;
    }

    public void deductBalance(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Haha Funny u do not get free money");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance -= amount;
    }

    public void addBalance(int amount) {
        balance += amount;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Key-Par Generation Error: ");
        }
    }

    @Override
    public String toString() {
        return "Account: " + ownerName +  " |  Balance: " + balance;
    }
}
