//
//  Compositum.swift
//  Swifting
//
//  Created by Julian Thaler on 05/03/24.
//

protocol Component {
   func add(component: Component)
   func display(indent: String)
}


struct Leaf: Component {
   private var name: String

   init(name: String) {
      self.name = name
   }

   func add(component: Component) {
      fatalError("Not executable!!! Is a Leaf")
   }

   func display(indent: String) {
      print("\(indent)\(name)")
   }
}

class Composite: Component {
   private var name: String
   private var components: [Component] = []

   init(name: String) {
      self.name = name
   }

   func add(component: Component) {
      components.append(component)
   }

   func display(indent: String) {
      print("\(indent)\(name)")
      for component: Component in components {
         component.display(indent: indent + "\t")
      }
   }
}

func main() {
   let root: Composite = Composite(name: "Dachboden")

   let smallBox1: Composite = Composite(name: "Kleine Schachtel 1")
   smallBox1.add(component: Leaf(name: "CD1"))
   smallBox1.add(component: Leaf(name: "CD2"))

   let smallBox2: Composite = Composite(name: "Kleine Schachtel 2")
   smallBox2.add(component: Leaf(name: "CD3"))
   smallBox2.add(component: Leaf(name: "CD4"))

   let mediumBox: Composite = Composite(name: "Mittlere Schachtel")
   mediumBox.add(component: smallBox1)
   mediumBox.add(component: Leaf(name: "LP"))

   let largeBox: Composite = Composite(name: "Große Schachtel")
   largeBox.add(component: mediumBox)
   largeBox.add(component: smallBox2)
   largeBox.add(component: Leaf(name: "Vinyl"))

   root.add(component: largeBox)

   root.display(indent: "")
}

main()
