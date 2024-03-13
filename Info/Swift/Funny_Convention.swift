/*
 Factory (Generator)
 */
import Foundation

main()
func main() {
   let booksData: [[String: String]] = [
      ["title": "Clean Code", "author": "Robert C. Martin", "year": "2008"],
      ["title": "Java ist auch eine Insel , Version 11", "author": "Christian Ullenboom", "year": "2018"],
      ["title": "Design Patterns", "author": "Erich Gamma, Richard Helm", "year": "1994"],
   ]
   var books: [Book] = []

   for bookData: [String: String] in booksData {
      if let title: String = bookData["title"],
         let author: String = bookData["author"],
         let yearString: String = bookData["year"],
         let year: Int = Int(yearString) {
         let book: Book = Book(author: author, title: title, yearPublished: year)
         books.append(book)
      }
   }
   let type: ListType = ListType.JSON
   let client: Client = Client()
   client.getBookListRepresentation(books: books, type: type)
}

protocol RepresentationGenerator {
   func gererateBookListRepresentation(books: [Book]) -> BookListRepresentation
}

protocol BookListRepresentation {
   var representation: [Book] { get }
   var numBooks: Int { get }

   func getRepresentation() -> String
   func getNumBooks() -> Int
}

struct Client {
   public func getBookListRepresentation(books: [Book], type: ListType) {
      if type == ListType.HTML {
         let gen: HTMLGenerator = HTMLGenerator()
         let pres: any BookListRepresentation = gen.gererateBookListRepresentation(books: books)
         print(pres.getRepresentation())
      } else if type == ListType.JSON {
         let gen: JSONGenerator = JSONGenerator()
         let presany: BookListRepresentation = gen.gererateBookListRepresentation(books: books)
         print(pres.getRepresentation())
      }
   }
}

struct HTMLGenerator: RepresentationGenerator {
   func gererateBookListRepresentation(books: [Book]) -> BookListRepresentation {
      return HTMLBooksList(rep: books, size: books.count)
   }
}

struct JSONGenerator: RepresentationGenerator {
   func gererateBookListRepresentation(books: [Book]) -> BookListRepresentation {
      return JSONBookList(rep: books, size: books.count)
   }
}

struct HTMLBooksList: BookListRepresentation {
   let numBooks: Int
   let representation: [Book]

   init(rep: [Book], size: Int) {
      numBooks = size
      representation = rep
   }

   func getRepresentation() -> String {
      var htmlString: String = "<ul>\n"

      for book: Book in representation {
         let listItem: String = "<li>\(book.title). \(book.author); \(book.yearPublished)</li>\n"
         htmlString.append(listItem)
      }

      htmlString.append("</ul>")

      return htmlString
   }

   func getNumBooks() -> Int {
      return numBooks
   }
}

struct JSONBookList: BookListRepresentation {
   let numBooks: Int
   let representation: [Book]

   init(rep: [Book], size: Int) {
      numBooks = size
      representation = rep
   }

   func getRepresentation() -> String {
      var booksArray: [[String: Any]] = [[String: Any]]()
      for book: Book in representation {
         booksArray.append([
            "author": book.author,
            "title": book.title,
            "year": String(book.yearPublished),
         ])
      }
      if let jsonData: Data = try? JSONSerialization.data(withJSONObject: ["Books": booksArray], options: .prettyPrinted),
         let jsonString: String = String(data: jsonData, encoding: .utf8) {
         return jsonString
      }
      return ""
   }

   func getNumBooks() -> Int {
      return numBooks
   }
}

struct Book {
   let author: String
   let title: String
   let yearPublished: Int
}

enum ListType {
   case HTML, JSON
}
