package dao

import model.Book
import reactivemongo.api.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.Future

trait BookDAO {
  def getAllBooks(page: Int, limit: Int): Future[Seq[BSONDocument]]

  def create(book: Book): Future[Boolean]

  def getById(id: BSONObjectID): Future[Option[BSONDocument]]

  def update(id: BSONObjectID, book: Book): Future[Option[BSONDocument]]

  def delete(id: BSONObjectID): Future[Boolean]
}
