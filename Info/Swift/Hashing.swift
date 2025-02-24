import Foundation

class Student: Codable {
  var firstName: String
  var lastName: String
  var birthDate: Date
  var address: String

  init(firstName: String, lastName: String, birthDate: Date, address: String) {
    self.firstName = firstName
    self.lastName = lastName
    self.birthDate = birthDate
    self.address = address
  }
}

class HashTable {
  private var buckets: [StudentNode?]
  private var itemCount: Int
  public var size: Int

  init(size: Int) {
    self.size = size
    self.buckets = Array(repeating: nil, count: size)
    self.itemCount = 0
  }

  private class StudentNode {
    var student: Student
    var next: StudentNode?

    init(student: Student, next: StudentNode? = nil) {
      self.student = student
      self.next = next
    }
  }

  private func hashFunction(key: String) -> Int {
    return abs(key.hashValue) % size
  }

  func insert(student: Student) {
    if loadFactor() > 0.75 {
      resize()
    }
    let index = hashFunction(key: student.lastName)
    let newNode = StudentNode(student: student)

    if buckets[index] == nil {
      buckets[index] = newNode
    } else {
      var node = buckets[index]
      while node?.next != nil {
        node = node?.next
      }
      node?.next = newNode
    }
    itemCount += 1
  }

  func search(lastName: String) -> Student? {
    let index = hashFunction(key: lastName)
    var node = buckets[index]

    while node != nil {
      if node!.student.lastName == lastName {
        return node!.student
      }
      node = node?.next
    }
    return nil
  }

  func delete(lastName: String) {
    let index = hashFunction(key: lastName)
    var node = buckets[index]
    var previous: StudentNode?

    while node != nil {
      if node!.student.lastName == lastName {
        if previous == nil {
          buckets[index] = node?.next
        } else {
          previous?.next = node?.next
        }
        itemCount -= 1
        return
      }
      previous = node
      node = node?.next
    }
  }

  func loadFactor() -> Double {
    return Double(itemCount) / Double(size)
  }

  private func resize() {
    let newSize = Int(Double(size) * 1.2)
    let newHashTable = HashTable(size: newSize)

    for bucket in buckets {
      var node = bucket
      while node != nil {
        newHashTable.insert(student: node!.student)
        node = node?.next
      }
    }

    self.buckets = newHashTable.buckets
    self.size = newSize
    print("Resized HashTable to \(self.size)")
  }

  func allStudents() -> [Student] {
    var students = [Student]()
    for bucket in buckets {
      var node = bucket
      while node != nil {
        students.append(node!.student)
        node = node?.next
      }
    }
    return students
  }
}

func loadStudents(from file: String) -> [Student] {
  let fileURL = URL(fileURLWithPath: file)
  do {
    let data = try Data(contentsOf: fileURL)
    let students = try JSONDecoder().decode([Student].self, from: data)
    return students
  } catch {
    print("Fehler beim Laden der Datei: \(error)")
    return []
  }
}

func saveStudents(_ students: [Student], to file: String) {
  let fileURL = URL(fileURLWithPath: file)
  do {
    let data = try JSONEncoder().encode(students)
    try data.write(to: fileURL)
  } catch {
    print("Fehler beim Speichern der Datei: \(error)")
  }
}

let dateFormatter = DateFormatter()
dateFormatter.dateFormat = "yyyy-MM-dd"

let studentsFromFile = loadStudents(from: "students.json")
let hashTable = HashTable(size: 28)

for student in studentsFromFile {
  hashTable.insert(student: student)
}

let student1 = Student(
  firstName: "Max", lastName: "Mustermann", birthDate: dateFormatter.date(from: "2005-01-01")!,
  address: "Musterstraße 1")
let student2 = Student(
  firstName: "Erika", lastName: "Musterfrau", birthDate: dateFormatter.date(from: "2005-02-02")!,
  address: "Musterstraße 2")
hashTable.insert(student: student1)
hashTable.insert(student: student2)

if hashTable.size < 100 {
  let firstNames = [
    "Erika", "John", "Jane", "Michael", "Emily", "David", "Sophia", "James", "Isabella", "Daniel",
  ]
  let lastNames = [
    "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez",
    "Martinez",
    "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson",
    "Martin",
    "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis",
    "Robinson",
    "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
    "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter",
    "Roberts",
    "Gomez", "Phillips", "Evans", "Turner", "Diaz", "Parker", "Cruz", "Edwards", "Collins", "Reyes",
    "Stewart", "Morris", "Morales", "Murphy", "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan",
    "Cooper",
    "Peterson", "Bailey", "Reed", "Kelly", "Howard", "Ramos", "Kim", "Cox", "Ward", "Richardson",
    "Watson", "Brooks", "Chavez", "Wood", "James", "Bennett", "Gray", "Mendoza", "Ruiz", "Hughes",
    "Price", "Alvarez", "Castillo", "Sanders", "Patel", "Myers", "Long", "Ross", "Foster",
    "Jimenez",
  ]
  let addresses = [
    "Musterstraße", "Main Street", "Elm Street", "Maple Avenue", "Oak Lane", "Pine Street",
    "Cedar Road", "Birch Drive", "Spruce Street", "Willow Avenue",
  ]

  for i in 0..<100 {
    let firstName = firstNames[i % firstNames.count]
    let lastName = lastNames[i]
    let birthDate = dateFormatter.date(
      from: "2005-\(String(format: "%02d", (i % 12) + 1))-\(String(format: "%02d", (i % 28) + 1))")!
    let address = "\(addresses[i % addresses.count]) \(i + 1)"

    let student = Student(
      firstName: firstName, lastName: lastName, birthDate: birthDate, address: address)
    hashTable.insert(student: student)
  }
}

if let foundStudent = hashTable.search(lastName: "Mustermann") {
  print("Gefunden: \(foundStudent.firstName) \(foundStudent.lastName)")
} else {
  print("Student nicht gefunden")
}

hashTable.delete(lastName: "Mustermann")
hashTable.delete(lastName: "Musterfrau")

if let foundStudent = hashTable.search(lastName: "Mustermann") {
  print("Gefunden: \(foundStudent.firstName) \(foundStudent.lastName)")
} else {
  print("Student nicht gefunden")
}

print("Belegungsfaktor: \(hashTable.loadFactor())")

let allStudents = hashTable.allStudents()
saveStudents(allStudents, to: "Info/Swift/students.json")
