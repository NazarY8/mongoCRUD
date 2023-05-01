package services

import dao.BookDAO
import model.Book
import reactivemongo.api.bson.{BSON, BSONObjectID}
import scala.util.{Failure, Success}
import scala.concurrent.Future
import scala.concurrent.duration._
import java.util.Timer
import java.util.TimerTask
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

class BookServiceImpl(bookDAO: BookDAO) extends BookService {

  override def create(title: String, author: String): Future[Boolean] = {
    bookDAO
      .create(Book(Some(title), Some(author)))
  }

  override def getById(id: BSONObjectID): Future[Book] = {
    bookDAO
      .getById(id)
      .flatMap {
        case Some(doc) =>
          val tryBook = BSON.readDocument[Book](doc)
          tryBook match {
            case Success(book) => Future.successful(book)
            case Failure(ex) => Future.failed(ex)
          }
        case None => Future.failed(new RuntimeException(s"No book found with id $id"))
      }
  }

  override def delete(id: BSONObjectID): Future[Boolean] = {
    bookDAO
      .delete(id)
  }

  /*
  The timer is needed so that the updated book is returned to us as a result.
  Since map, flatMap, for-comprehension, do not guarantee such a result due to the fact that the library is asynchronous
  and very fast. If you do not set a small delay through the Timer,
  reactive mongo can read information faster than updating it, and as a result,
  the user will see old data, although it will be updated in the database.
   */
  override def update(id: BSONObjectID, title: Option[String], author: Option[String]): Future[Book] = {
    val updatedInfo = Book(title, author)
    bookDAO
      .update(id, updatedInfo)
      .flatMap { _ =>
        val delay = 100.milliseconds
        val promise = Promise[Book]()
        val timer = new Timer()
        timer.schedule(new TimerTask {
          override def run(): Unit = {
            promise.completeWith(getById(id))
            timer.cancel()
          }
        }, delay.toMillis)
        promise.future
      }
  }

  override def getAll(page: Int, limit: Int): Future[Seq[Book]] = {
    for {
      books <- bookDAO.getAllBooks(page, limit)
    } yield
      books.flatMap(bsonDoc => BSON.read[Book](bsonDoc).toOption)
  }

}
