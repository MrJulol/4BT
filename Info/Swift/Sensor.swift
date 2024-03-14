import Foundation
main()
func main() {
   let station: WeatherStation = WeatherStation()
   let sensor1: Sensor = Sensor(id: 1, ort: "Ort A", value: 20.0)
   let sensor2: Sensor = Sensor(id: 2, ort: "Ort B", value: 25.0)

   station.addSensor(sensor: sensor1)
   station.addSensor(sensor: sensor2)

   print("PULL-Variante:")
   station.checkSensorsPull()

   print("\nPUSH-Variante:")
   station.checkSensorsPush()
}

protocol SensorProtocol {
   var id: Int { get }
   var ort: String { get }
   var value: Double { get set }
   func changeValue()
}

class Sensor: SensorProtocol {
   let id: Int
   let ort: String
   var value: Double

   init(id: Int, ort: String, value: Double) {
      self.id = id
      self.ort = ort
      self.value = value
   }

   func changeValue() {
      value = Double.random(in: -10.0 ... 40.0)
   }
}

class WeatherStation {
   var sensors: [SensorProtocol] = []

   func addSensor(sensor: SensorProtocol) {
      sensors.append(sensor)
   }

   func checkSensorsPull() {
      for sensor: any SensorProtocol in sensors {
         sensor.changeValue()
         print("Sensor \(sensor.id) in \(sensor.ort) - Wert: \(sensor.value)")
      }
   }

   func checkSensorsPush() {
      for sensor: any SensorProtocol in sensors {
         notify(sensor: sensor)
      }
   }

   func notify(sensor: SensorProtocol) {
      print("Sensor \(sensor.id) in \(sensor.ort) - Wert: \(sensor.value)")
   }
}
