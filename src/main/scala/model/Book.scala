package model

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class Book(title: Option[String], author: Option[String])

object Book {
  implicit val reader: BSONDocumentReader[Book] = Macros.reader[Book]
  implicit val writer: BSONDocumentWriter[Book] = Macros.writer[Book]
}


