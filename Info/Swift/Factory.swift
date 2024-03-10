//
//  Singelton.swift
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
}

class MotorA: Motor {
   func description() {
      print("Motor of Car A")
   }
}

class MotorB: Motor {
   func description() {
      print("Motor of Car B")
   }
}

class WheelA: Wheel {
   func description() {
      print("Wheel of Car A")
   }
}

class WheelB: Wheel {
   func description() {
      print("Wheel of Car B")
   }
}

class CarFactoryA: CarFactory {
   func createMotor() -> Motor {
      return MotorA()
   }

   func createWheels() -> Wheel {
      return WheelA()
   }
}

class CarFactoryB: CarFactory {
   func createMotor() -> Motor {
      return MotorB()
   }

   func createWheels() -> Wheel {
      return WheelB()
   }
}

func main() {
   let type: String = "A"
   var factory: CarFactory
   if type == "A" {
      factory = CarFactoryA()
   } else {
      factory = CarFactoryB()
   }
   let motor: Motor = factory.createMotor()
   let wheels: Wheel = factory.createWheels()

   print("Cartype: " + type)
   motor.description()
   wheels.description()
}

