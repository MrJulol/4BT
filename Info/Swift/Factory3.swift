import Foundation
main()
func main(){
   var factory: Factory = AmericaCarFactory()
   var someCar: Car = factory.createCar()
   var specific:Specification = factory.createSpecification()
   someCar.assemble()
   specific.display()
   factory = EuropeCarFactory()
   someCar = factory.createCar()
   specific = factory.createSpecification()
   someCar.assemble()
   specific.display()    

}
protocol Car {
	func assemble() -> Void
}
protocol Factory {
	func createCar() -> Car
	func createSpecification() -> Specification
}
protocol Specification {
	func display() -> Void
}

struct AmericaCarSpecification : Specification {
   func display() -> Void{
      print("American Specification: Safety compliant with local regulations")
   }
}
struct EuropeSpecification : Specification {
   func display() {
      print("Europe Specification: Fuel emissions compliant with EU standards")
   }
} 
struct Sedan : Car {
   func assemble() {
      print("Assembling Sedan car.")
   }
}
struct Hatchback : Car {
   func assemble() {
      print("Assemblng Hatchback car.")
   }
}


struct AmericaCarFactory : Factory {
   func createCar() -> any Car {
      return Sedan()
   }
   func createSpecification() -> any Specification {
      return AmericaCarSpecification()
   }
}
struct EuropeCarFactory : Factory {
	func createCar() -> any Car {
      return Hatchback()
   }
   func createSpecification() -> any Specification {
      return EuropeSpecification()
   }
}
