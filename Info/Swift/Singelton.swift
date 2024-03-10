//
//  Singelton.swift
//  Swifting
//
//  Created by Julian Thaler on 05/03/24.
//

import Foundation
import Swift

main()

func main() {
   let logger: Logger = Logger.getInstance()
   logger.setLogDateTime(logDateTime: true)
   logger.logToConsole(message: "Console Logging")
   logger.logToFile(message: "File Logging", path: "/Users/riven/MyProjects/swifting/logfile.txt")
}

extension Date {
   static func getCurrentDate() -> String {
      let dateFormatter: DateFormatter = DateFormatter()
      dateFormatter.dateFormat = "dd/MM/yyyy HH:mm:ss"
      return dateFormatter.string(from: Date())
   }
}

class Logger {
   private static var INSTANCE: Logger!
   private var logDateTime: Bool

   private init() {
		print("Created Signelton instance!")
      logDateTime = true
   }

   public static func getInstance() -> Logger {
      if INSTANCE == nil {
         INSTANCE = Logger()
      }
      return INSTANCE
   }

   public func formatPrint(message: String) -> String {
      var newMessage: String = message
      if self.logDateTime {
         newMessage = newMessage + " : " + Date.getCurrentDate()
      }
      return newMessage
   }

   public func setLogDateTime(logDateTime: Bool) {
      self.logDateTime = logDateTime
   }

   public func logToConsole(message: String) {
      print(formatPrint(message: message))
   }

   public func logToFile(message: String, path: String) {
      let message2: String = formatPrint(message: message) + "\n"
      let fileURL: URL = URL(fileURLWithPath: path)

      if FileManager.default.fileExists(atPath: path) {
         do {
            let fileHandle: FileHandle = try FileHandle(forWritingTo: fileURL)
            let currentPos: UInt64 = try fileHandle.seekToEnd()
            if let data: Data = message2.data(using: .utf8) {
               try fileHandle.seek(toOffset: currentPos)
               fileHandle.write(data)
               print("Logged to existing file!")
            }
         } catch {
            print("ERROR logging to existing file!")
         }
      } else {
         do {
            try message2.write(to: fileURL, atomically: true, encoding: .utf8)
            print("Created new Logfile & Logged to File")
         } catch {
            print("Error logging to new file!")
         }
      }
   }
}
