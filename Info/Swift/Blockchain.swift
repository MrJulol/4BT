import CryptoKit
import Foundation

enum Exceptions: Error {
   case NegativeMoneyError
   case InsufficientBalanceError
   
}
extension Date {
   static func getCurrentDate() -> String {
      let dateFormatter: DateFormatter = DateFormatter()
      dateFormatter.dateFormat = "dd/MM/yyyy HH:mm:ss"
      return dateFormatter.string(from: Date())
   }
}
class Hasher {
    static func sha256(input: String) -> String {
        if let inputData = input.data(using: .utf8) {
            let hashedData = SHA256.hash(data: inputData)
            let hashedString = hashedData.compactMap { String(format: "%02x", $0) }.joined()
            return hashedString
        }
        return ""
    }
}
struct Transaction {
   let sender: Account
   let receiver: Account
   let amount: Int

   init(sender: Account, receiver: Account, amount: Int){
      if sender.balance < amount {
         print("Not Balance")
         self.sender = Account(ownerName: "DUMMY", balance: 0)
         self.receiver = Account(ownerName: "DUMMY", balance: 0)
         self.amount = 0
      }else {
         self.sender = sender
         self.receiver = receiver
         self.amount = amount
         do{
            try self.sender.deductBalance(amount: amount)
         } catch Exceptions.InsufficientBalanceError {
            print("Not Balance")
         } catch Exceptions.NegativeMoneyError {
            print("Money Not Money")
         } catch {
            print("Else")
         }
         self.receiver.addBalance(amount: amount)
      }
   }
}


class Account {
   let ownerName: String
   var balance: Int

   init(ownerName: String = "", balance: Int = 0){
      self.ownerName = ownerName
      self.balance = balance
   }
   func deductBalance(amount: Int) throws{
      if amount < 0 {
         throw Exceptions.NegativeMoneyError
      }
      if amount > self.balance {
         throw Exceptions.InsufficientBalanceError
      }
      balance-=amount
   }
   func addBalance(amount: Int){
      self.balance += amount
   }
   func desc() -> String {
      return "Account: \(self.ownerName) | Balance: \(self.balance)"
   }
}

class Block {
   let timestamp: String
   let transactions: [Transaction]
   var previousHash: String = ""
   var hash: String = ""

   init(transactions: [Transaction]){
      self.transactions = transactions
      self.timestamp = Date.getCurrentDate()
   }
   func calculateHash(nonce: Int) -> String {
      let dataToHash:String = "\(self.previousHash)\(self.timestamp)\(nonce)"
      return Hasher.sha256(input: dataToHash)
   }
   func desc() -> String {
      return "\n\tBlock: timestamp: \(self.timestamp), transactions: \(self.transactions.count), previous Hash: \(self.previousHash), hash: \(self.hash)"
   }
}
class Blockchain {
   var chain: [Block] = []
   let pre: Int
   var nonce: Int = 0

   init(pre: Int){
      self.pre = pre
   }
   func add(block: Block){
      if !chain.isEmpty {
         let previousBlock: Block = chain[chain.count-1]
         block.previousHash = previousBlock.hash
      }else{
         let genesis: Block = Block(transactions: [])
         genesis.previousHash = "null"
         genesis.hash = "Genesis: \(genesis.calculateHash(nonce: 0))"
         chain.append(genesis)
         block.previousHash = genesis.calculateHash(nonce: 0)
      }
      mineBlock(block: block)
      chain.append(block)
   }
   func mineBlock(block: Block){
      let preString: String = String(repeating: "0", count: self.pre)
      while true {
         let hashAttempt: String = block.calculateHash(nonce: self.nonce)
         if hashAttempt.starts(with: preString){
            block.hash = hashAttempt
            break
         }else{
            self.nonce+=1
         }
      }
      print("Block added: \(block.desc())\tNonce: \(self.nonce)\n")
   }
   func desc() -> String{
      var log: String = ""
      for b: Block in self.chain {
         log.append("Block: \(b.hash)\n")
         log.append("PreviousHash: \(b.previousHash)\n")
         log.append("Transactions: \(b.transactions.count)\n")
         for t: Transaction in b.transactions {
            log.append("\tFrom \(t.sender.ownerName)")
            log.append(" to \(t.receiver.ownerName)")
            log.append(" : \(t.amount)\n")
         }
         log.append("\n")
      }
      return log
   }
}
class BlockchainController {
   static func createBlockchain(tom: Account, max: Account, eva: Account, blockchain: Blockchain){
      TransactionController.createTransaction(transactions: [Transaction(sender: tom, receiver: max, amount: 300), Transaction(sender: tom, receiver: eva, amount: 100)], blockchain: blockchain)
      TransactionController.createTransaction(transactions: [Transaction(sender: max, receiver: eva, amount: 1000)], blockchain: blockchain)
      TransactionController.createTransaction(transactions: [Transaction(sender: tom, receiver: eva, amount: 500), Transaction(sender: tom, receiver: max, amount: 100), Transaction(sender: eva, receiver: max, amount: 30)], blockchain: blockchain)
   }
   static func printAccountInfo(tom: Account, max: Account, eva: Account){
      print("\n\nAccount Information: \n\(max.desc())\n\(tom.desc())\n\(eva.desc())")
   }
}
class TransactionController {
   static func createTransaction(transactions: [Transaction], blockchain: Blockchain){
      blockchain.add(block: Block(transactions: transactions))
   }
   
}

let tom: Account = Account(ownerName: "Tom", balance: 1000)
let max: Account = Account(ownerName: "Max", balance: 1000)
let eva: Account = Account(ownerName: "Eva", balance: 1000)
let blockchain: Blockchain = Blockchain(pre: 2)

BlockchainController.createBlockchain(tom: tom, max: max, eva: eva, blockchain: blockchain)
print(blockchain.desc())
BlockchainController.printAccountInfo(tom: tom, max: max, eva: eva)

