import com.softwaremill.macwire._
import controllers.BookController
import dao.{BookDAO, BookDAOImpl}
import services.BookServiceImpl

trait BookDI {
  lazy val bookDao: BookDAO = wire[BookDAOImpl]
  lazy val bookService: BookServiceImpl = wire[BookServiceImpl]
  lazy val bookController: BookController = wire[BookController]
}
