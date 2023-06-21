package play.api.mvc

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{ JsPath, Json, JsonValidationError, __ }
import play.api.mvc.FieldValidation.{
  FieldHasInvalidValue,
  FieldIsEmpty,
  FieldIsMissing,
  FieldMustBeArray,
  FieldMustBeBigDecimal,
  FieldMustBeByte,
  FieldMustBeInteger,
  FieldMustBeLong,
  FieldMustBeNumber,
  FieldMustBeShort,
  FieldValidationError,
  MultipleResultsForField
}

class FieldValidationSpec extends PlaySpec {

  "A 'field is missing' validation" should {
    val validation = FieldIsMissing(JsonPath(__ \ "field"))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the type of the error as errorName" in {
      (validationJson \ "errorName").as[String] mustBe "fieldIsMissing"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Field is mandatory"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field has invalid value' validation" should {
    val validation = FieldHasInvalidValue(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "errorName").as[String] mustBe "fieldHasInvalidValue"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Field has invalid value"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field is empty' validation" should {
    val validation = FieldIsEmpty(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "errorName").as[String] mustBe "fieldIsEmpty"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Field cannot be empty"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'multiple results for field' validation" should {
    val validation = MultipleResultsForField(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "errorName").as[String] mustBe "multipleResultsForField"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Multiple results for the given path"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be an array' validation" should {
    val validation = FieldMustBeArray(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "errorName").as[String] mustBe "fieldMustBeArray"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Field must be an array"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be an integer' validation" should {
    val validation = FieldMustBeInteger(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "errorName").as[String] mustBe "fieldMustBeInteger"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Field must be an integer"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a short' validation" should {
    val validation = FieldMustBeShort(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "errorName").as[String] mustBe "fieldMustBeShort"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Field must be a short"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a byte' validation" should {
    val validation = FieldMustBeByte(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "errorName").as[String] mustBe "fieldMustBeByte"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Field must be a byte"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a long' validation" should {
    val validation = FieldMustBeLong(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "errorName").as[String] mustBe "fieldMustBeLong"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Field must be a long"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a number' validation" should {
    val validation = FieldMustBeNumber(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "errorName").as[String] mustBe "fieldMustBeNumber"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Field must be a number"
    }

    "have concerned field defined" in {
      (validationJson \ "field").isDefined
    }
  }

  "A 'field must be a bigdecimal' validation" should {
    val validation = FieldMustBeBigDecimal(JsonPath(JsPath()))
    val validationJson = Json.toJson[FieldValidationError](validation)

    "contain the right error code" in {
      (validationJson \ "errorName").as[String] mustBe "fieldMustBeBigDecimal"
    }

    "contain the right message" in {
      (validationJson \ "message").as[String] mustBe "Field must be a BigDecimal type"
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
    }
  }
}
