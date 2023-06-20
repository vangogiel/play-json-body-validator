package play.api.mvc

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{ Json, __ }
import play.api.mvc.FieldValidation.{ FieldIsMissing, FieldValidationError }

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
}
