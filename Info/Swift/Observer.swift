main()
func main() {
   let inv: Investor = Investor(name: "One")
   let inv2: Investor = Investor(name: "Two")
   let market: Market = Market()
   let stock: Stock = Stock(name: "apple", stockPrice: 10.0)

   market.registerObserver(observer: inv)
   market.registerObserver(observer: inv2)
   market.setStockPrice(stock: stock, newPrice: 11.5)
}

protocol StockObserver {
   func update(stock: Stock)
}

protocol StockMarket {
   func registerObserver(observer: StockObserver)
   func notifyObservers(stock: Stock)
}

class Investor: StockObserver {
   var name: String

   init(name: String) {
      self.name = name
   }

   func update(stock: Stock) {
      print("\(name) wurde informiert: \(stock.name) / \(stock.price)")
   }
}

class Stock {
   let name: String
   var price: Double

   init(name: String, stockPrice: Double) {
      self.name = name
      price = stockPrice
   }
}

class Market: StockMarket {
   private var observers: [any StockObserver] = [StockObserver]()

   public func registerObserver(observer: StockObserver) {
      observers.append(observer)
   }

   func notifyObservers(stock: Stock) {
      for observer: any StockObserver in observers {
         observer.update(stock: stock)
      }
   }

   public func setStockPrice(stock: Stock, newPrice: Double) {
      stock.price = newPrice
      notifyObservers(stock: stock)
   }
}
