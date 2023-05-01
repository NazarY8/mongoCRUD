package dao

import db.{MongoConfig, MongoDBConnection}
import model.Book
import pureconfig.generic.auto._
import pureconfig.ConfigSource
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, BSONObjectID}
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future


class BookDAOImpl extends BookDAO {
  private val docNumber = 1
  private val one = 1
  private val config: MongoConfig = ConfigSource.file("config.yaml").loadOrThrow[MongoConfig]
  private val dbConnection: MongoDBConnection = MongoDBConnection(config.mongoURL, config.dbName)
  private val collection: Future[BSONCollection] = dbConnection.getCollection(config.collectionName)

  override def getAllBooks(page: Int, limit: Int): Future[Seq[BSONDocument]] = {
    println(s"page $page")
    println(s"limit $limit")

    collection
      .flatMap { col =>
        col.find(BSONDocument.empty)
          .skip((page - one) * limit)
          .cursor[BSONDocument]()
          .collect[Seq](limit, Cursor.FailOnError[Seq[BSONDocument]]())
      }
  }

  override def create(book: Book): Future[Boolean] = {
    collection
      .flatMap { col =>
        col
          .insert
          .one(book)
          .map(res => res.writeErrors.isEmpty)
      }
  }

  override def getById(id: BSONObjectID): Future[Option[BSONDocument]] = {
    collection
      .flatMap(_.find(BSONDocument("_id" -> id), Option.empty[BSONDocument]).one[BSONDocument])
  }

  override def delete(id: BSONObjectID): Future[Boolean] =
    collection
      .flatMap(_.delete()
        .one(BSONDocument("_id" -> id))
        .map(_.n == docNumber))

  override def update(id: BSONObjectID, book: Book): Future[Option[BSONDocument]] = {
    collection
      .flatMap(_.update(ordered = false)
        .one(
          q = BSONDocument("_id" -> id),
          u = BSONDocument("$set" -> BSONDocument("title" -> book.title, "author" -> book.author)),
          upsert = false,
          multi = false)
        .map(_.n == docNumber)
      )

    getById(id)
  }
}
