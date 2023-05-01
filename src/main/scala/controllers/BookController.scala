package controllers

import cats.data
import cats.data.Validated.{Invalid, Valid}
import cats.effect.IO
import cats.implicits.toTraverseOps
import controllers.BookController.{createBook, deleteBook, getAll, readBook, updateBook}
import io.circe.generic.auto.exportEncoder
import io.circe.syntax.EncoderOps
import org.http4s.circe.jsonEncoder
import org.http4s.{HttpRoutes, ParseFailure, Response}
import reactivemongo.api.bson.BSONObjectID
import services.BookServiceImpl
import org.http4s.dsl.io._

import scala.util.{Failure, Success}


class BookController(bookService: BookServiceImpl) {
  def bookRoutes(): HttpRoutes[IO] = {
    val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
      case GET -> Root / "book" / "pagination" :? PageDecoder(pageResult) +& LimitDecoder(limitResult) =>
        getAll(bookService, pageResult, limitResult)
      case GET -> Root / "book" / "read" :? IdDecoder(id) => {
        readBook(bookService, id)
      }
      case POST -> Root / "book" / "create" :? TitleDecoder(title) +& AuthorDecoder(author) =>
        createBook(bookService, title, author)
      case PUT -> Root / "book" / "update" :? IdDecoder(id) +& OptionTitleDecoder(title) +& OptionAuthorDecoder(author) => {
        updateBook(bookService, id, title, author)
      }
      case DELETE -> Root / "book" / "delete" :? IdDecoder(id) => {
        deleteBook(bookService, id)
      }
    }

    routes
  }
}

object BookController {
  val defaultLimit: Int = 5
  val defaultPageIndex: Int = 1

  def bookAsJson(bookService: BookServiceImpl, page: Int, limit: Int): IO[Response[IO]] = {
    IO.fromFuture(IO(bookService.getAll(page, limit)))
      .flatMap { books =>
        Ok(books.asJson)
      }
  }

  def getAll(bookService: BookServiceImpl, pageResult: Option[data.ValidatedNel[ParseFailure, Int]],
             limitResult: Option[data.ValidatedNel[ParseFailure, Int]]): IO[Response[IO]] = {
    (pageResult.sequence, limitResult.sequence) match {
      case (Valid(Some(page)), Valid(Some(limit))) =>
        bookAsJson(bookService, page, limit)
      case (Valid(Some(page)), Valid(None)) =>
        // Handle the case when the limit parameter is missing
        bookAsJson(bookService, page, defaultLimit)
      case (Valid(None), Valid(Some(limit))) =>
        // Handle the case when the page parameter is missing
        bookAsJson(bookService, defaultPageIndex, limit)
      case (Valid(None), Valid(None)) =>
        // Handle the case when both page and limit parameters are missing
        bookAsJson(bookService, defaultPageIndex, defaultLimit)
      case (Invalid(pageErrors), Invalid(limitErrors)) =>
        BadRequest(pageErrors.toList.map(_.sanitized).mkString(", ") + "; " + limitErrors.toList.map(_.sanitized).mkString(", "))
      case (Invalid(pageErrors), _) =>
        BadRequest(pageErrors.toList.map(_.sanitized).mkString(", "))
      case (_, Invalid(limitErrors)) =>
        BadRequest(limitErrors.toList.map(_.sanitized).mkString(", "))
    }
  }

  def createBook(bookService: BookServiceImpl, title: String, author: String): IO[Response[IO]] = {
    val missingParams = List(
      if (title.isEmpty) Some("title") else None,
      if (author.isEmpty) Some("author") else None
    ).flatten

    missingParams match {
      case Nil => // Both parameters are provided
        IO.fromFuture(IO(bookService.create(title, author))).flatMap { result =>
          Ok(result.asJson)
        }
      case missingParam :: Nil => // Only one parameter is missing
        BadRequest(s"${missingParam.capitalize} parameter is missing")
      case _ => // Both parameters are missing
        BadRequest("Title and Author parameters are missing")
    }
  }

  def readBook(bookService: BookServiceImpl, id: String): IO[Response[IO]] = {
    BSONObjectID.parse(id) match {
      case Success(value) => {
        IO.fromFuture(IO(bookService.getById(value))).flatMap { book =>
          Ok(book.asJson)
        }
      }
      case Failure(ex) => throw new RuntimeException(ex)
    }
  }

  def updateBook(bookService: BookServiceImpl, id: String, title: Option[String], author: Option[String]): IO[Response[IO]] = {
    BSONObjectID.parse(id) match {
      case Success(value) =>
        IO.fromFuture(IO(bookService.update(value, title, author))).flatMap { book =>
          Ok(book.asJson)
        }
      case Failure(ex) => throw new RuntimeException(ex)
    }
  }

  def deleteBook(bookService: BookServiceImpl, id: String): IO[Response[IO]] = {
    BSONObjectID.parse(id) match {
      case Success(value) => {
        IO.fromFuture(IO(bookService.delete(value))).flatMap {
          status => Ok(status.asJson)
        }
      }
      case Failure(ex) => throw new RuntimeException(ex)
    }
  }
}