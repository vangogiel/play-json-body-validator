package play.api.mvc

import play.api.libs.json._
import play.api.mvc.FieldValidation.FieldValidationError

import scala.collection.Seq

object Errors {
  sealed trait GeneralError {
    def status: Int
    def detail: String
  }

  case object BodyIsEmpty extends GeneralError {
    val status: Int = 400
    val detail: String = "You must provide body in your request"
  }

  case object BodyIsNotJson extends GeneralError {
    val status: Int = 400
    val detail: String = "You must provide valid json content with your request"
  }

  case class BodyDoesNotMatchSchema(errors: Seq[FieldValidationError]) extends GeneralError {
    val status: Int = 400
    val detail: String = "The message body does not match JSON schema"
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
