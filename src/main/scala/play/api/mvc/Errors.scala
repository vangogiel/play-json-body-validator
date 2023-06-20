package play.api.mvc

import julienrf.json.derived
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils
import play.api.libs.json.{ JsPath, JsString, JsonValidationError, OWrites, __ }
import play.api.mvc.FieldValidation.FieldValidationError

import scala.collection.Seq

object Errors {
  sealed trait GeneralError {
    val message: String
  }

  case object BodyIsEmpty extends GeneralError {
    val message: String = "You must provide body in your request"
  }

  case object BodyIsNotJson extends GeneralError {
    val message: String = "You must provide valid json content with your request"
  }

  case class BodyDoesNotMatchSchema(errors: Seq[FieldValidationError]) extends GeneralError {
    val message: String = "The message body does not match JSON schema"
  }

  object GeneralError {
    implicit val writes: OWrites[GeneralError] = error =>
      derived.flat
        .owrites[GeneralError]((__ \ "errorName").write[String].contramap[String](StringUtils.uncapitalize))
        .writes(error)
        .+(("message", JsString(error.message)))
  }

  object BodyDoesNotMatchSchema {
    def fromJsErrors(errors: Seq[(JsPath, Seq[JsonValidationError])]): BodyDoesNotMatchSchema = {
      BodyDoesNotMatchSchema(errors = for {
        pathAndValidationError <- errors
        (jsPath, jsonValidationErrors) = pathAndValidationError
        validationError <- jsonValidationErrors
      } yield FieldValidationError.convertFromJsonValidationError(jsPath, validationError))
    }
  }
}
