//
//  Fabrik2.swift
//  Swifting
//
//  Created by Julian Thaler on 05/03/24.
//

import Foundation
main()
func main() {
   let treasureChest:TreasureChest = TreasureChest.newInstanceWithGemstoneWeight(weight: 3)
   treasureChest.say()
}

struct TreasureChest {
   let goldDoubloonWeight: Int
   let gemstoneWeight: Int

   private init() {
      gemstoneWeight = 0
      goldDoubloonWeight = 0
   }

   private init(GoldWeight: Int) {
      goldDoubloonWeight = GoldWeight
      gemstoneWeight = 0
   }

   private init(GemWeight: Int) {
      gemstoneWeight = GemWeight
      goldDoubloonWeight = 0
   }

   private init(weight1: Int, weight2: Int) {
      goldDoubloonWeight = weight1
      gemstoneWeight = weight2
   }

   static func newInstance() -> TreasureChest {
      return TreasureChest()
   }

   static func newInstanceWithGoldDoubloonWeight(weight: Int) -> TreasureChest {
      return TreasureChest(GoldWeight: weight)
   }

   static func newInstanceWithGemstoneWeight(weight: Int) -> TreasureChest {
      return TreasureChest(GemWeight: weight)
   }

   static func newInstanceWithGoldDoubloonWeightAndGemStoneWeight(weight1: Int, weight2: Int) -> TreasureChest {
      return TreasureChest(weight1: weight1, weight2: weight2)
   }

   public func say() {
      if gemstoneWeight == 0 && goldDoubloonWeight == 0 {
         print("I am an empty chest")
      } else {
         print("I am a chest with value: Gold: \(goldDoubloonWeight) Gems: \(gemstoneWeight)")
      }
   }
}
