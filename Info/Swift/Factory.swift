//
//  Factory.swift
//  Swifting
//
//  Created by Julian Thaler on 05/03/24.
//

import Foundation
main()

protocol Motor {
   func description() -> Void
}

protocol Wheel {
   func description() -> Void
}

protocol CarFactory {
   func createMotor() -> Motor
   func createWheels() -> Wheel
   func getType() -> Character
}

struct MotorA: Motor {
   func description() {
      print("Motor of Car A")
   }
}

struct MotorB: Motor {
   func description() {
      print("Motor of Car B")
   }
}

struct WheelA: Wheel {
   func description() {
      print("Wheel of Car A")
   }
}

struct WheelB: Wheel {
   func description() {
      print("Wheel of Car B")
   }
}

struct CarFactoryA: CarFactory {
   func createMotor() -> Motor {
      return MotorA()
   }

   func createWheels() -> Wheel {
      return WheelA()
   }
   func getType() -> Character {
      return "A"
   }
}

struct CarFactoryB: CarFactory {
   func createMotor() -> Motor {
      return MotorB()
   }

   func createWheels() -> Wheel {
      return WheelB()
   }
   func getType() -> Character {
      return "B"
   }
}
struct Car{
   var wheels:Wheel
   var motor:Motor
   var type:Character
   init(wheels:Wheel, motor:Motor, type:Character){
      self.motor = motor
      self.wheels = wheels
      self.type = type
   }
   func display(){
      print("Im Car \(self.type)")
      self.wheels.description()
      self.motor.description()
   }
}

func main() {
   var factory: CarFactory = CarFactoryA()
   let car:Car = Car(wheels:factory.createWheels(),motor:factory.createMotor(), type: factory.getType())
   car.display()
   print("\n__________________________________\n")
   factory = CarFactoryB();
   let car2:Car = Car(wheels:factory.createWheels(),motor:factory.createMotor(), type: factory.getType())
   car2.display()
}

