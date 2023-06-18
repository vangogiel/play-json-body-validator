package play.api.mvc

import cats.syntax.either._
import play.api.libs.json.{ Json, Reads }
import play.api.mvc.Errors.{ BodyDoesNotMatchSchema, BodyIsEmpty, GeneralError }

import scala.concurrent.ExecutionContext

abstract class RequestBodyParser(parse: PlayBodyParsers)(implicit val ec: ExecutionContext) extends Results {
  def parseRequest[A]()(implicit reads: Reads[A]): BodyParser[A] = {
    BodyParser(header =>
      parse.tolerantJson
        .apply(header)
        .map(either => {
          val result: Either[GeneralError, A] = either match {
            case Right(json) =>
              json
                .validate[A]
                .asEither
                .leftMap[GeneralError](_ => BodyDoesNotMatchSchema)
            case Left(_) => Left(BodyIsEmpty)
          }
          result.leftMap(error => BadRequest(Json.toJson[GeneralError](error)))
        })
    )
  }
}
