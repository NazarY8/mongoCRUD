import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT

object WebServer extends IOApp with BookDI {
  override def run(args: List[String]): IO[ExitCode] = {
    val bookRoutes = bookController.bookRoutes().orNotFound

    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(bookRoutes)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
