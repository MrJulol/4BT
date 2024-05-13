public class BlockchainView {
    /**
    Very hideous print function
     **/
    public void displayBlockchain(Blockchain blockchain){
        StringBuilder sb = new StringBuilder();
        for (Block block : blockchain.getChain()) {
            sb.append("\n");
            if(block.getPreviousHash().equals("null")){
                sb.append("Block: GENESIS: ").append(block.getHash()).append("\n");
            }else{
                sb.append("Block: ").append(block.getHash()).append("\n");
            }

            sb.append("Completely Rehashed Block: ").append(block.calculateHash()).append("\n");
            sb.append("Previous Hash: ").append(block.getPreviousHash()).append("\n");
            sb.append("Transactions: ").append(block.getTransactions().size()).append("\n");

            for (Transaction transaction : block.getTransactions()) {
                sb.append(TransactionController.printTransaction(transaction));
            }
            sb.append("\n");
            for(int i = 0; i<3;i++){
                sb.append("\t".repeat(3));
                sb.append("\u21d3").append("\n");
            }
        }
        System.out.println(sb);
    }
}
