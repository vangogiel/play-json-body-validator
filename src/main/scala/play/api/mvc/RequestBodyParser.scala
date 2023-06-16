package play.api.mvc

import cats.syntax.either._
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc.Errors.{ BodyIsEmpty, GeneralError }

import scala.concurrent.ExecutionContext

abstract class RequestBodyParser(parse: PlayBodyParsers)(implicit val ec: ExecutionContext) extends Results {
  def parseRequest(): BodyParser[JsValue] = {
    BodyParser(header =>
      parse.tolerantJson
        .apply(header)
        .map(either => {
          val result: Either[GeneralError, JsValue] = either match {
            case Right(json) => Right(json)
            case Left(_)     => Left(BodyIsEmpty)
          }
          result.leftMap(error => BadRequest(Json.toJson[GeneralError](error)))
        })
    )
  }
}
