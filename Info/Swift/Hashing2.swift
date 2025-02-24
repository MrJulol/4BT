import Foundation

func hash(_ s: String, _ m: Int, _ a: Int) -> Int {
  var h = 0
  for char in s.utf8 {
    h = (a * h + Int(char)) % m
  }
  return h
}

func readNamesFromFile(_ filename: String) -> [String] {
  var names = [String]()
  do {
    let content = try String(contentsOfFile: filename, encoding: .utf8)
    names = content.components(separatedBy: .newlines).filter { !$0.isEmpty }
  } catch {
    print("Error reading file: \(error)")
  }
  return names
}

func countCollisions(names: [String], m: Int, a: Int) -> Int {
  var hashTable = [Int](repeating: 0, count: m)
  var collisions = 0
  for name in names {
    let hashValue = hash(name, m, a)
    if hashTable[hashValue] != 0 {
      collisions += 1
    } else {
      hashTable[hashValue] = 1
    }
  }
  return collisions
}

func findBestParameters(names: [String]) {
  let mRange = 3327...4096
  let step = (mRange.upperBound - mRange.lowerBound + 1) / 10

  let group = DispatchGroup()
  let queue = DispatchQueue(label: "com.findBestParameters.queue", attributes: .concurrent)

  for i in 0..<10 {
    queue.async(group: group) {
      let localRangeStart = mRange.lowerBound + i * step
      var localRangeEnd = localRangeStart + step - 1
      if i == 9 {
        localRangeEnd = mRange.upperBound
      }

      var bestA = 0
      var bestM = 0
      var minCollisions = Int.max
      for m in localRangeStart...localRangeEnd {
        for a in 1..<m {
          let collisions = countCollisions(names: names, m: m, a: a)
          if collisions < minCollisions {
            minCollisions = collisions
            bestA = a
            bestM = m
          }
        }
        print("Done \(m)")
      }

      DispatchQueue.main.async {
        print(
          "Best parameters found: a = \(bestA), m = \(bestM) with \(minCollisions) collisions in Area: \(localRangeStart)-\(localRangeEnd)"
        )
      }
    }
  }

  group.wait()
}

let names = readNamesFromFile("/Users/riven/dev/school/4BT/Info/Swift/listHashing.txt")
print("Number of entries: \(names.count)")
findBestParameters(names: names)
