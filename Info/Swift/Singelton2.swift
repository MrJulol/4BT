import Foundation
main()
func main() {
   print(LogLevel.DEBUG.rawValue)

   let log: Logger = Logger.getInstance()
   let testLog: Logger = Logger.getInstance()

   print("Are both Loggers the same reference? : \(testLog === log)\n")

   log.setLogLevel(level: LogLevel.DEBUG)
   log.log(level: LogLevel.TRACE, message: "trace level log message")
   log.log(level: LogLevel.DEBUG, message: "debug level log message")
   log.log(level: LogLevel.INFO, message: "info level log message")
   log.log(level: LogLevel.WARN, message: "warn level log message")
   log.log(level: LogLevel.ERROR, message: "error level log message")

   log.displayLogs()
}

extension Date {
   static func getCurrentDate() -> String {
      let dateFormatter: DateFormatter = DateFormatter()
      dateFormatter.dateFormat = "dd/MM/yyyy HH:mm:ss"
      return dateFormatter.string(from: Date())
   }
}

class Logger {
   private static var instance: Logger? = Logger()
   private var logs: [(LogLevel, String, String)] = []
   private var level: Int = 1

   public static func getInstance() -> Logger {
      if instance == nil {
         instance = Logger()
      }
      return Logger.instance!
   }

   func log(level: LogLevel, message: String) {
      let path: String = "/Users/riven/Programming Stuff/School/4BT/Info/Swift/logfile-Singelton2.txt"
      logs.append((level, message, Date.getCurrentDate()))
      if level.rawValue >= self.level {
         let errorLog: String = "\(Date.getCurrentDate()) : \(level) : \(message)\n"
         logToFile(message: errorLog, path: path)
         print(errorLog)
      }
   }

   public func logToFile(message: String, path: String) {
      let error: String = "\(message)\n"
      let fileURL: URL = URL(fileURLWithPath: path)

      if FileManager.default.fileExists(atPath: path) {
         do {
            let fileHandle: FileHandle = try FileHandle(forWritingTo: fileURL)
            let currentPos: UInt64 = try fileHandle.seekToEnd()
            if let data: Data = error.data(using: .utf8) {
               try fileHandle.seek(toOffset: currentPos)
               fileHandle.write(data)
               print("Logged to existing file!")
            }
         } catch {
            print("ERROR logging to existing file!")
         }
      } else {
         do {
            try error.write(to: fileURL, atomically: true, encoding: .utf8)
            print("Created new Logfile & Logged to File")
         } catch {
            print("Error logging to new file!")
         }
      }
   }

   func setLogLevel(level: LogLevel) {
      self.level = level.rawValue
   }

   func displayLogs() {
      print("Displaying all logged Messages: ")
      for log: (LogLevel, String, String) in logs {
         print("Time: \(log.2)\tLog Severity: \(log.0) \tMessage: \(log.1)")
      }
   }

   func test() {
      print("WORKING")
   }
}

enum LogLevel: Int {
   case TRACE = 1, DEBUG, INFO, WARN, ERROR
}
