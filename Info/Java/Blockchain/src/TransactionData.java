/*
Only Transaction-Controller should create a Transaction
Rest uses the Transaction-Data record
 */
public record TransactionData(Account sender, Account receiver, int amount){
}