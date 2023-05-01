package db

import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, MongoConnection}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

//Note: example MongoDB URL: "mongodb://localhost:27017/bookDB"
case class MongoDBConnection(mongoURL: String, dbName: String) {
  // Note: Do not use lazy val while creating driver.
  val mongoDriver: AsyncDriver = AsyncDriver()

  lazy val parsedURIFuture: Future[ParsedURI] =
    MongoConnection
      .fromString(mongoURL)

  lazy val connection: Future[MongoConnection] =
    parsedURIFuture
      .flatMap(u => mongoDriver.connect(u))

  def getCollection(collectionName: String): Future[BSONCollection] = {
    connection
      .flatMap(_.database(dbName))
      .map(_.collection(collectionName))
  }
}



