package play.api.mvc

import julienrf.json.derived
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils
import play.api.libs.json.{ JsPath, JsString, JsonValidationError, Writes, __ }
import play.api.mvc.PlayJsonValidationErrors.{
  ExpectedBigDecimal,
  ExpectedBigInteger,
  ExpectedByte,
  ExpectedInt,
  ExpectedJsArray,
  ExpectedLong,
  ExpectedNumber,
  ExpectedShort,
  FieldEmpty,
  MultipleResults,
  PathMissing
}

object FieldValidation {
  sealed trait FieldValidationError {
    def message: String
    def field: JsonPath
  }

  case class FieldIsMissing(field: JsonPath) extends FieldValidationError {
    val message = "Field is mandatory"
  }

  case class FieldHasInvalidValue(field: JsonPath) extends FieldValidationError {
    val message = "Field has invalid value"
  }

  case class FieldIsEmpty(field: JsonPath) extends FieldValidationError {
    val message = "Field cannot be empty"
  }

  case class MultipleResultsForField(field: JsonPath) extends FieldValidationError {
    val message = "Multiple results for the given path"
  }

  case class FieldMustBeArray(field: JsonPath) extends FieldValidationError {
    val message = "Field must be an array"
  }

  case class FieldMustBeInteger(field: JsonPath) extends FieldValidationError {
    val message = "Field must be an Int type"
  }

  case class FieldMustBeShort(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a Short type"
  }

  case class FieldMustBeByte(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a Byte type"
  }

  case class FieldMustBeLong(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a Long type"
  }

  case class FieldMustBeNumber(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a Double or a Float type"
  }

  case class FieldMustBeBigDecimal(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a BigDecimal type"
  }

  case class FieldMustBeBigInteger(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a BigInteger type"
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
        case PathMissing        => FieldIsMissing(path)
        case FieldEmpty         => FieldIsEmpty(path)
        case MultipleResults    => MultipleResultsForField(path)
        case ExpectedJsArray    => FieldMustBeArray(path)
        case ExpectedInt        => FieldMustBeInteger(path)
        case ExpectedShort      => FieldMustBeShort(path)
        case ExpectedByte       => FieldMustBeByte(path)
        case ExpectedLong       => FieldMustBeLong(path)
        case ExpectedNumber     => FieldMustBeNumber(path)
        case ExpectedBigDecimal => FieldMustBeBigDecimal(path)
        case ExpectedBigInteger => FieldMustBeBigInteger(path)
        case _                  => FieldHasInvalidValue(path)
      }
    }
  }
}
