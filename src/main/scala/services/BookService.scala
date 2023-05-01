package services

import model.Book
import reactivemongo.api.bson.BSONObjectID

import scala.concurrent.Future

trait BookService {
  def getAll(page: Int, limit: Int): Future[Seq[Book]]

  def create(title: String, author: String): Future[Boolean]

  def getById(id: BSONObjectID): Future[Book]

  def delete(id: BSONObjectID): Future[Boolean]

  def update(id: BSONObjectID, title: Option[String], author: Option[String]): Future[Book]
}
