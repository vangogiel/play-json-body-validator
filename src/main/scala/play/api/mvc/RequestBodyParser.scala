package play.api.mvc

import cats.syntax.either._
import play.api.libs.json.JsValue

import scala.concurrent.ExecutionContext

abstract class RequestBodyParser(parse: PlayBodyParsers)(implicit val ec: ExecutionContext) extends Results {
  def parseRequest(): BodyParser[JsValue] = {
    BodyParser(header =>
      parse.tolerantJson
        .apply(header)
        .map(either => {
          val result: Either[String, JsValue] = either match {
            case Right(json) => Right(json)
            case _           => Left("error")
          }
          result.leftMap(errorMessage => BadRequest(errorMessage))
        })
    )
  }
}
