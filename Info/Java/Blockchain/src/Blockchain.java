import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain;

    private final int prefix;

    public Blockchain(int prefix) {
        this.prefix = prefix;
        chain = new ArrayList<>();
    }
    /**
        Adds the provided block to the blockchain.
        In case of an empty blockchain, creates the GENESIS block
        Mines the block before adding
        Verifies every Block of the Blockchain using the Keys
    **/
    public void add(Block block) {
        if (!chain.isEmpty()) {
            Block previousBlock = chain.getLast();
            block.setPreviousHash(previousBlock.getHash());
        } else {
            Block genesis = new Block(new ArrayList<>());
            genesis.setPreviousHash("null");
            genesis.setNonce(0);
            genesis.setHash(genesis.calculateHash());
            mineBlock(genesis);
            chain.add(genesis);
            block.setPreviousHash(genesis.calculateHash());
        }
        mineBlock(block);
        chain.add(block);
        for(Block block1: chain){
            for(Transaction t: block1.getTransactions()){
                if(!t.verifyTransaction()){
                    throw new IllegalArgumentException("VERIFYING NOT GOOD");
                }
            }
        }

    }
    /**
    Mine the provided Block until the for the Blockchain appropriate prefix of 0s is met
        Sets the nonce of the Block to the calculated nonce
    **/
    private void mineBlock(Block block) {
        int nonceHere = 0;
        String prefixString = new String(new char[this.prefix]).replace('\0', '0');
        block.setNonce(0);
        while (true) {
            String hashAttempt = block.calculateHash();
            if (hashAttempt.startsWith(prefixString)) {
                block.setHash(hashAttempt);
                block.setNonce(nonceHere);
                break;
            } else {
                nonceHere++;
                block.setNonce(nonceHere);
            }
        }
        System.out.println("\nBlock added: " + block);
    }

    public List<Block> getChain() {
        return chain;
    }

}
