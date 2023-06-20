package play.api.mvc

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{ JsPath, Json, JsonValidationError, __ }
import play.api.mvc.FieldValidation.{
  FieldHasInvalidValue,
  FieldIsEmpty,
  FieldIsMissing,
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
    }
  }
}
