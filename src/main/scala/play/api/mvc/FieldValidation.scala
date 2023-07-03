package play.api.mvc

import julienrf.json.derived
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils
import play.api.libs.json.{ JsPath, JsString, JsonValidationError, Writes, __ }
import play.api.mvc.PlayJsonValidationErrors.{
  ExpectedBigDecimal,
  ExpectedBigInteger,
  ExpectedBoolean,
  ExpectedByte,
  ExpectedCharacter,
  ExpectedDate,
  ExpectedEnumString,
  ExpectedInt,
  ExpectedJsArray,
  ExpectedLong,
  ExpectedNumber,
  ExpectedObject,
  ExpectedShort,
  ExpectedString,
  ExpectedUUID,
  ExpectedValidEnumValues,
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
    val message = "Field must be a number"
  }

  case class FieldMustBeBigDecimal(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a BigDecimal type"
  }

  case class FieldMustBeBigInteger(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a BigInteger type"
  }

  case class FieldMustBeValidEnumValue(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a valid enum value"
  }

  case class FieldMustBeEnumString(field: JsonPath) extends FieldValidationError {
    val message = "Field must be an enum String"
  }

  case class FieldMustBeBoolean(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a Boolean type"
  }

  case class FieldMustBeString(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a String type"
  }

  case class FieldMustBeObject(field: JsonPath) extends FieldValidationError {
    val message = "Field must be an object"
  }

  case class FieldMustBeCharacter(field: JsonPath) extends FieldValidationError {
    val message = "Field must be a character"
  }

  case class FieldMustBeUUID(field: JsonPath) extends FieldValidationError {
    val message = "Field must be UUID"
  }

  case class FieldMustBeDate(field: JsonPath) extends FieldValidationError {
    val message = "Field must be Date"
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
        case ExpectedUUID            => FieldMustBeUUID(path)
        case ExpectedDate            => FieldMustBeDate(path)
        case _                       => FieldHasInvalidValue(path)
      }
    }
  }
}
