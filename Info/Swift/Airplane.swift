import Foundation
main()
func main() {
   let selectedClass: Packet = Packet.Business
   let didPayMore: Bool = true
   let Items: CabinLayout = CabinLayout(name: "Total: ")

   if selectedClass == Packet.Entertainment {
      putEntertainment(items: Items, pay: didPayMore)
   } else if selectedClass == Packet.Nightcare {
      putNightcare(items: Items, pay: didPayMore)
   } else if selectedClass == Packet.Business {
      putEntertainment(items: Items, pay: didPayMore)
      putNightcare(items: Items, pay: didPayMore)
      putBusiness(items: Items, pay: didPayMore)
   }
   Items.display(indent: "")
}

func putEntertainment(items: CabinLayout, pay: Bool) {
   let layout: CabinLayout = CabinLayout(name: "Entertainment: ")
   layout.add(item: CabinItem(name: "Touchscreen"))
   if pay {
      layout.add(item: CabinItem(name: "Bosekopfhörer"))
   }
   items.add(item: layout)
}

func putNightcare(items: CabinLayout, pay: Bool) {
   let layout: CabinLayout = CabinLayout(name: "Nightcare: ")
   layout.add(item: CabinItem(name: "Hausschuhe"))
   layout.add(item: CabinItem(name: "Kuscheldecke"))
   if pay {
      layout.add(item: CabinItem(name: "Schlummertrunk"))
   }
   items.add(item: layout)
}

func putBusiness(items: CabinLayout, pay: Bool) {
   let layout: CabinLayout = CabinLayout(name: "Business")
   layout.add(item: CabinItem(name: "Telefon"))
   if pay {
      layout.add(item: CabinItem(name: "WIFI"))
   }
   items.add(item: layout)
}

enum Packet {
   case Entertainment, Nightcare, Business
}

protocol Item {
   func add(item: Item)
   func display(indent: String)
}

struct CabinItem: Item {
   private var name: String
   func add(item: any Item) {
      fatalError("This is a Leaf. NO ADDING HERE")
   }

   func display(indent: String) {
      print("\(indent) | \(name)")
   }

   init(name: String) {
      self.name = name
   }
}

class CabinLayout: Item {
   private var name: String
   private var items: [Item] = []
   func add(item: any Item) {
      items.append(item)
   }

   func display(indent: String) {
      print("\(indent) | \(name)")
      for item: Item in items {
         item.display(indent: indent + "\t")
      }
   }

   init(name: String) {
      self.name = name
   }
}
