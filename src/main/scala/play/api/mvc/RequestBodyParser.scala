package play.api.mvc

import cats.syntax.either._
import play.api.libs.json.{ JsValue, Json, Reads }
import play.api.mvc.Errors.{ BodyDoesNotMatchSchema, BodyIsEmpty, BodyIsNotJson, GeneralError }

import scala.concurrent.ExecutionContext

class RequestBodyParser(parser: PlayBodyParsers)(implicit val ec: ExecutionContext) extends Results {
  def parseRequest[A]()(implicit reads: Reads[A]): BodyParser[A] = {
    createBodyParser(
      parser,
      either => {
        val result: Either[GeneralError, A] = either match {
          case Right(json) =>
            json
              .validate[A]
              .asEither
              .leftMap[GeneralError](errors => BodyDoesNotMatchSchema.fromJsErrors(errors))
          case Left(false) => Left(BodyIsEmpty)
          case Left(true)  => Left(BodyIsNotJson)
        }
        result.leftMap(error => BadRequest(Json.toJson[GeneralError](error)))
      }
    )
  }

  private def createBodyParser[A](
      parser: PlayBodyParsers,
      f: Either[Boolean, JsValue] => Either[Result, A]
  ): BodyParser[A] = {
    BodyParser(header =>
      parser.tolerantJson
        .apply(header)
        .map(either => f(either.leftMap(_ => header.hasBody)))
    )
  }
}
