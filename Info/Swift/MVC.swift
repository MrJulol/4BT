main()
func main() {
   let st1: Student = Student(name: "Julian", ID: "000000")
   // let st2: Student = Student(name: "Leo", ID: "000001")
   let view: StudentView = StudentView()
   let controller: StudentController = StudentController(view: view, model: st1)

   controller.updateView()
   controller.setStudentName(name: "Leo")
   controller.updateView()
}

class Student {
   var name: String
   var ID: String

   init(name: String, ID: String) {
      self.name = name
      self.ID = ID
   }

   func setName(name: String) {
      self.name = name
   }

   func setID(ID: String) {
      self.ID = ID
   }

   func getName() -> String {
      return name
   }

   func getID() -> String {
      return ID
   }
}

struct StudentView {
   func printStudent(name: String, ID: String) {
      print("Student: \(ID)\tName: \(name)")
   }
}

class StudentController {
   private var view: StudentView
   private var model: Student

   init(view: StudentView, model: Student) {
      self.view = view
      self.model = model
   }

   public func getStudentName() -> String {
      return model.getName()
   }

   public func getStudentID() -> String {
      return model.getID()
   }

   public func setStudentName(name: String) {
      model.setName(name: name)
   }

   public func setStudentID(ID: String) {
      model.setID(ID: ID)
   }

   public func updateView() {
      view.printStudent(name: model.getName(), ID: model.getID())
   }
}
