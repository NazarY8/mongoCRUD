import org.http4s.dsl.impl.OptionalValidatingQueryParamDecoderMatcher
import org.http4s.dsl.io.{OptionalQueryParamDecoderMatcher, QueryParamDecoderMatcher}

package object controllers {

  object TitleDecoder extends QueryParamDecoderMatcher[String]("title")

  object AuthorDecoder extends QueryParamDecoderMatcher[String]("author")

  object IdDecoder extends QueryParamDecoderMatcher[String]("id")

  object OptionTitleDecoder extends OptionalQueryParamDecoderMatcher[String]("title")

  object OptionAuthorDecoder extends OptionalQueryParamDecoderMatcher[String]("author")

  object PageDecoder extends OptionalValidatingQueryParamDecoderMatcher[Int]("page")

  object LimitDecoder extends OptionalValidatingQueryParamDecoderMatcher[Int]("limit")
}

