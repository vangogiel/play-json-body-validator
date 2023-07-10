package play.api.mvc

import julienrf.json.derived.{ DerivedOWrites, NameAdapter, TypeTag, TypeTagOWrites }
import play.api.libs.json.{ JsNumber, JsObject, JsString, OWrites }
import play.api.mvc.Errors.GeneralError
import shapeless.Lazy

object ImplicitWrites {
  implicit val generalErrorWrites: OWrites[GeneralError] = error => {
    JsObject(List("status" -> JsNumber(error.status))) ++
      JsObject(List("title" -> JsString(camelToCapitalisedSnakeCase(error.getClass.getSimpleName)))) ++
      JsObject(List("detail" -> JsString(error.detail))) ++
      customOWrites[GeneralError]().writes(error)
  }

  private def customOWrites[A](
      adapter: NameAdapter = NameAdapter.identity
  )(implicit
      derivedOWrites: Lazy[DerivedOWrites[A, TypeTag.ShortClassName]]
  ): OWrites[A] = derivedOWrites.value.owrites(flat(), adapter)

  def flat(): TypeTagOWrites =
    new TypeTagOWrites {
      def owrites[A](typeName: String, owrites: OWrites[A]): OWrites[A] =
        OWrites[A](a => owrites.writes(a))
    }

  private def camelToCapitalisedSnakeCase(s: String): String = {
    "_?[A-Z][a-z\\d]+".r.findAllMatchIn(s).map(_.group(0).toUpperCase).mkString("_")
  }
}
