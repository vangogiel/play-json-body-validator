package play.api.mvc

import play.api.libs.json.{ JsPath, JsonValidationError }
import play.api.mvc.PlayJsonValidationErrors._

object FieldValidation {
  sealed trait FieldValidationError {
    def detail: String
    def field: JsonPath
  }

  case class FieldIsMissing(field: JsonPath) extends FieldValidationError {
    val detail = "Field is mandatory"
  }

  case class FieldHasInvalidValue(field: JsonPath) extends FieldValidationError {
    val detail = "Field has invalid value"
  }

  case class FieldIsEmpty(field: JsonPath) extends FieldValidationError {
    val detail = "Field cannot be empty"
  }

  case class MultipleResultsForField(field: JsonPath) extends FieldValidationError {
    val detail = "Multiple results for the given path"
  }

  case class FieldMustBeArray(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be an array"
  }

  case class FieldMustBeInteger(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be an Int type"
  }

  case class FieldMustBeShort(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be a Short type"
  }

  case class FieldMustBeByte(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be a Byte type"
  }

  case class FieldMustBeLong(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be a Long type"
  }

  case class FieldMustBeNumber(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be a number"
  }

  case class FieldMustBeBigDecimal(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be a BigDecimal type"
  }

  case class FieldMustBeBigInteger(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be a BigInteger type"
  }

  case class FieldMustBeValidEnumValue(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be a valid enum value"
  }

  case class FieldMustBeEnumString(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be an enum String"
  }

  case class FieldMustBeBoolean(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be a Boolean type"
  }

  case class FieldMustBeString(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be a String type"
  }

  case class FieldMustBeObject(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be an object"
  }

  case class FieldMustBeCharacter(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be a character"
  }

  case class FieldMustBeUuid(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be UUID"
  }

  case class FieldMustBeDate(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be Date"
  }

  case class FieldMustBeIsoFormatDate(field: JsonPath) extends FieldValidationError {
    val detail = "Field must be ISO Date"
  }

  object FieldValidationError {
    def convertFromJsonValidationError(jsPath: JsPath, error: JsonValidationError): FieldValidationError = {
      val path = JsonPath(jsPath)
      error.message match {
        case PathMissing             => FieldIsMissing(path)
        case FieldEmpty              => FieldIsEmpty(path)
        case MultipleResults         => MultipleResultsForField(path)
        case ExpectedJsArray         => FieldMustBeArray(path)
        case ExpectedInt             => FieldMustBeInteger(path)
        case ExpectedShort           => FieldMustBeShort(path)
        case ExpectedByte            => FieldMustBeByte(path)
        case ExpectedLong            => FieldMustBeLong(path)
        case ExpectedNumber          => FieldMustBeNumber(path)
        case ExpectedBigDecimal      => FieldMustBeBigDecimal(path)
        case ExpectedBigInteger      => FieldMustBeBigInteger(path)
        case ExpectedValidEnumValues => FieldMustBeValidEnumValue(path)
        case ExpectedEnumString      => FieldMustBeEnumString(path)
        case ExpectedBoolean         => FieldMustBeBoolean(path)
        case ExpectedString          => FieldMustBeString(path)
        case ExpectedObject          => FieldMustBeObject(path)
        case ExpectedCharacter       => FieldMustBeCharacter(path)
        case ExpectedUUID            => FieldMustBeUuid(path)
        case ExpectedDate            => FieldMustBeDate(path)
        case ExpectedISOFormatDate   => FieldMustBeIsoFormatDate(path)
        case _                       => FieldHasInvalidValue(path)
      }
    }
  }
}
