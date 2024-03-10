import Foundation
main()
func removeSmallInts(list: [Int], lim: Int) -> [Int] {
   return list.filter({ (element: Int) -> Bool in
      element >= lim
   })
}

func containsDuplicate(list: [Int]) -> Bool {
   var dic: [Int: Int] = [:]
   for item: Int in list {
      dic[item] = (dic[item] ?? 0) + 1
   }
   for item: Int in dic.values {
      if item > 1 {
         return true
      }
   }
   return false
}

func inBoth(list1: [Int], list2: [Int]) -> [Int] {
   var both: [Int] = []
   for element: Int in list1 {
      if list2.contains(element) {
         both.append(element)
      }
   }
   return both
}

func inEither(list1: [Int], list2: [Int]) -> [Int] {
   var either: [Int] = list1
   for element: Int in list2 {
      if !list1.contains(element) {
         either.append(element)
      }
   }
   return either
}

func mostFrequent<T: Hashable>(array: [T]) -> (value: T, count: Int)? {
   let counts: [T: Int] = array.reduce(into: [:]) { $0[$1, default: 0] += 1 }
   if let (value:T, cou:Int) = counts.max(by: { $0.1 < $1.1 }) {
      return (value, count)
   }
   return nil
}

func main() {
   let numbers: [Int] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
   let numbers2: [Int] = [1, 2, 5, 11, 20, 2, 10]
   print(inEither(list1: numbers, list2: numbers2))
   print(mostFrequent(array: numbers2) as Any)
}
