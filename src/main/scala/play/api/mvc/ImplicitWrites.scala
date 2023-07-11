package play.api.mvc

import julienrf.json.derived.{ DerivedOWrites, NameAdapter, TypeTag, TypeTagOWrites }
import play.api.libs.json.{ JsNumber, JsObject, JsString, OWrites, Writes }
import play.api.mvc.Errors.GeneralError
import play.api.mvc.FieldValidation.FieldValidationError
import shapeless.Lazy

object ImplicitWrites {
  implicit val generalErrorWrites: OWrites[GeneralError] = error => {
    JsObject(
      List(
        "status" -> JsNumber(error.status),
        "title" -> JsString(camelToCapitalisedSnakeCase(error.getClass.getSimpleName)),
        "detail" -> JsString(error.detail)
      )
    ) ++ customOWrites[GeneralError]().writes(error)
  }

  implicit val fieldValidationWrites: Writes[FieldValidationError] = error =>
    JsObject(
      List(
        "title" -> JsString(camelToCapitalisedSnakeCase(error.getClass.getSimpleName)),
        "detail" -> JsString(error.detail)
      )
    ) ++ customOWrites[FieldValidationError]().writes(error)

  private def customOWrites[A]()(implicit derivedOWrites: Lazy[DerivedOWrites[A, TypeTag.ShortClassName]]): OWrites[A] =
    derivedOWrites.value.owrites(flat(), NameAdapter.identity)

  private def flat(): TypeTagOWrites = new TypeTagOWrites {
    def owrites[A](typeName: String, owrites: OWrites[A]): OWrites[A] =
      OWrites[A](a => owrites.writes(a))
  }

  private def camelToCapitalisedSnakeCase(s: String): String = {
    "_?[A-Z][a-z\\d]+".r.findAllMatchIn(s).map(_.group(0).toUpperCase).mkString("_")
  }
}
