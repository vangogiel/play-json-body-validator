package play.api.mvc

import julienrf.json.derived
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils
import play.api.libs.json.{ JsPath, JsString, JsonValidationError, Writes, __ }

object FieldValidation {
  sealed trait FieldValidationError {
    def message: String
    def field: JsonPath
  }

  case class FieldIsMissing(field: JsonPath) extends FieldValidationError {
    val message = "Field is mandatory"
  }

  object FieldValidationError {
    implicit val writes: Writes[FieldValidationError] = error =>
      derived.flat
        .owrites[FieldValidationError]((__ \ "errorName").write[String].contramap[String](StringUtils.uncapitalize))
        .writes(error)
        .+("message" -> JsString(error.message))

    def convertFromJsonValidationError(jsPath: JsPath, error: JsonValidationError): FieldValidationError = {
      val path = JsonPath(jsPath)
      error.message match {
        case _ => FieldIsMissing(path)
      }
    }
  }
}
