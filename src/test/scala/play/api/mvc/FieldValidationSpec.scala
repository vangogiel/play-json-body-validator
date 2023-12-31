package play.api.mvc

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{ JsPath, Json, JsonValidationError, __ }
import play.api.mvc.FieldValidation.{
  FieldHasInvalidValue,
  FieldIsEmpty,
  FieldIsMissing,
  FieldMustBeArray,
  FieldMustBeBigDecimal,
  FieldMustBeBigInteger,
  FieldMustBeBoolean,
  FieldMustBeByte,
  FieldMustBeCharacter,
  FieldMustBeDate,
  FieldMustBeEnumString,
  FieldMustBeIsoFormatDate,
  FieldMustBeInteger,
  FieldMustBeLong,
  FieldMustBeNumber,
  FieldMustBeObject,
  FieldMustBeShort,
  FieldMustBeString,
  FieldMustBeUuid,
  FieldMustBeValidEnumValue,
  FieldValidationError,
  MultipleResultsForField
}
import play.api.mvc.ImplicitWrites.fieldValidationWrites

class FieldValidationSpec extends PlaySpec {

  "A 'field is missing' validation" should {
    val validation = FieldIsMissing(JsonPath(__ \ "field"))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the type of the error as title" in {
      (validationJson \ "title").as[String] mustBe "FIELD_IS_MISSING"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field is mandatory"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field has invalid value' validation" should {
    val validation = FieldHasInvalidValue(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_HAS_INVALID_VALUE"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field has invalid value"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field is empty' validation" should {
    val validation = FieldIsEmpty(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_IS_EMPTY"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field cannot be empty"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'multiple results for field' validation" should {
    val validation = MultipleResultsForField(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "MULTIPLE_RESULTS_FOR_FIELD"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Multiple results for the given path"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be an array' validation" should {
    val validation = FieldMustBeArray(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_ARRAY"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be an array"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be an integer' validation" should {
    val validation = FieldMustBeInteger(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_INTEGER"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be an Int type"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a short' validation" should {
    val validation = FieldMustBeShort(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_SHORT"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be a Short type"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a byte' validation" should {
    val validation = FieldMustBeByte(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_BYTE"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be a Byte type"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a long' validation" should {
    val validation = FieldMustBeLong(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_LONG"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be a Long type"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a number' validation" should {
    val validation = FieldMustBeNumber(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_NUMBER"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be a number"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a bigdecimal' validation" should {
    val validation = FieldMustBeBigDecimal(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_BIG_DECIMAL"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be a BigDecimal type"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a biginteger' validation" should {
    val validation = FieldMustBeBigInteger(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_BIG_INTEGER"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be a BigInteger type"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a valid enum value' validation" should {
    val validation = FieldMustBeValidEnumValue(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_VALID_ENUM_VALUE"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be a valid enum value"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be an enum string' validation" should {
    val validation = FieldMustBeEnumString(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_ENUM_STRING"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be an enum String"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a Boolean type' validation" should {
    val validation = FieldMustBeBoolean(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_BOOLEAN"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be a Boolean type"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a String type' validation" should {
    val validation = FieldMustBeString(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_STRING"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be a String type"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be an object' validation" should {
    val validation = FieldMustBeObject(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_OBJECT"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be an object"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a character' validation" should {
    val validation = FieldMustBeCharacter(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_CHARACTER"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be a character"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be UUID' validation" should {
    val validation = FieldMustBeUuid(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_UUID"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be UUID"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be Date' validation" should {
    val validation = FieldMustBeDate(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_DATE"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be Date"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be ISO Format Date' validation" should {
    val validation = FieldMustBeIsoFormatDate(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "title").as[String] mustBe "FIELD_MUST_BE_ISO_FORMAT_DATE"
    }

    "contain the right detail" in {
      (validationJson \ "detail").as[String] mustBe "Field must be ISO Date"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A field validation error" when {
    "receives Play Json validation error" should {
      "correctly match 'path is missing' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.PathMissing)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldIsMissing]
      }

      "correctly match 'field has invalid value' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.FieldHasInvalidValue)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldHasInvalidValue]
      }

      "correctly match 'field is empty' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.FieldEmpty)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldIsEmpty]
      }

      "correctly match 'multiple results for field' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.MultipleResults)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[MultipleResultsForField]
      }

      "correctly match 'field must be an array' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedJsArray)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeArray]
      }

      "correctly match 'field must be an integer' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedInt)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeInteger]
      }

      "correctly match 'field must be a short' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedShort)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeShort]
      }

      "correctly match 'field must be a byte' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedByte)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeByte]
      }

      "correctly match 'field must be a long' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedLong)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeLong]
      }

      "correctly match 'field must be a number' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedNumber)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeNumber]
      }

      "correctly match 'field must be a bigdecimal' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedBigDecimal)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeBigDecimal]
      }

      "correctly match 'field must be a biginteger' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedBigInteger)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeBigInteger]
      }

      "correctly match 'field must be a valid enum value' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedValidEnumValues)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeValidEnumValue]
      }

      "correctly match 'field must be an enum string' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedEnumString)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeEnumString]
      }

      "correctly match 'field must be a Boolean type' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedBoolean)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeBoolean]
      }

      "correctly match 'field must be a String type' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedString)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeString]
      }

      "correctly match 'field must be an object' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedObject)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeObject]
      }

      "correctly match 'field must be a character' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedCharacter)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeCharacter]
      }

      "correctly match 'field must be UUID' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedUUID)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeUuid]
      }

      "correctly match 'field must be Date' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedDate)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeDate]
      }

      "correctly match 'field must be ISO Format Date' error" in {
        val error = JsonValidationError(PlayJsonValidationErrors.ExpectedISOFormatDate)
        val validationError = FieldValidationError.convertFromJsonValidationError(JsPath(), error)

        validationError mustBe a[FieldMustBeIsoFormatDate]
      }
    }
  }
}
