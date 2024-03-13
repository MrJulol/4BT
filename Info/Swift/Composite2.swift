import Foundation
main()
protocol Component {
   func add(component: Component)
   func display(indent: String)
}
protocol Department : Component {

}

struct FinancialDepartment: Department {
   private var number: Int
   private var name: String
   
   init(number: Int, name: String){
      self.number = number
      self.name = name
   }
   func add(component: any Component) {
      fatalError("Not executable!!! Is a Leaf")
   }
   func display(indent: String) {
      print("\(indent)\(self.number): \(self.name)")
   }
}
struct SalesDepartment: Department {
   private var number: Int
   private var name: String
   
   init(number: Int, name: String){
      self.number = number
      self.name = name
   }
   func add(component: any Component) {
      fatalError("Not executable!!! Is a Leaf")
   }
   func display(indent: String) {
      print("\(indent)\(self.number): \(self.name)")
   }
}
class HeadDepartment : Component {
   private let number: Int
   private let name: String
   private var components: [Component] = []

   init(number: Int, name: String){
      self.number = number
      self.name = name
   }
   func add(component: Component) {
      components.append(component)
   }
   func display(indent:String) {
      print("\(indent)\(self.number): \(self.name)")
      for component: Component in self.components {
         component.display(indent: indent + "\t")
      }
   }
}
func main(){

   let sales: Department = SalesDepartment(number: 1, name: "Sales Department")
   let financial: Department = FinancialDepartment(number: 2, name: "Financial Department")
   let head: HeadDepartment = HeadDepartment(number: 3, name: "Head Department")

   head.add(component: sales)
   head.add(component: financial)
   head.display(indent: "")

}
