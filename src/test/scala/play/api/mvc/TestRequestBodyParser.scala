package play.api.mvc

import akka.actor.ActorSystem
import akka.util.ByteString
import io.circe.Encoder
import org.scalatest.concurrent.Eventually
import org.scalatestplus.play.PlaySpec
import play.api.http.Status.BAD_REQUEST
import play.api.libs.json.{ JsValue, Json, OFormat }
import play.api.test.FakeRequest
import play.api.test.Helpers.{ contentAsJson, defaultAwaitTimeout, status, stubPlayBodyParsers }

import scala.concurrent.ExecutionContext.Implicits.global

case class TestRequest(
    firstParameter: String,
    secondParameter: String
)

object TestRequest {
  implicit val format: OFormat[TestRequest] = Json.format[TestRequest]
  implicit val encoder: Encoder[TestRequest] = Encoder.forProduct2(
    "firstParameter",
    "secondParameter"
  )(l => (l.firstParameter, l.secondParameter))
}

class TestRequestBodyParser extends PlaySpec with Eventually {
  implicit val actorSystem: ActorSystem = ActorSystem()

  val bodyParser = new TestBodyParser(stubPlayBodyParsers)

  "Parsing a request body" when {
    "the body is valid and the request" should {
      "return a claas" in {
        val outcome = bodyParser
          .parseRequest[TestRequest]()
          .apply(FakeRequest())
          .run(ByteString(mockJson))
          .map(_.toOption.get)

        eventually(outcome.value.get.get mustBe testRequest)
      }
    }

    "the body is empty" should {
      val outcome = bodyParser
        .parseRequest[JsValue]()
        .apply(FakeRequest())
        .run()
        .map(_.left.getOrElse(Results.ImATeapot))

      "return BadRequest in the response" in {
        status(outcome) mustBe BAD_REQUEST
      }

      "return an error stating that the body is empty" in {
        (contentAsJson(outcome) \ "errorName").as[String] mustBe "bodyIsEmpty"
        (contentAsJson(outcome) \ "message").as[String] mustBe "You must provide body in your request"
      }
    }

    "the body doest not match schema" should {
      val outcome = bodyParser
        .parseRequest[TestRequest]()
        .apply(FakeRequest())
        .run(ByteString("{\"firstParameter\": \"firstValue\"}"))
        .map(_.left.getOrElse(Results.ImATeapot))

      "return BadRequest in the response" in {
        status(outcome) mustBe BAD_REQUEST
      }

      "return an error stating that the body does not match schema" in {
        (contentAsJson(outcome) \ "errorName").as[String] mustBe "bodyDoesNotMatchSchema"
        (contentAsJson(outcome) \ "message").as[String] mustBe "The message body does not match JSON schema"
      }
    }
  }

  private val mockJson: String =
    "{\n    \"firstParameter\": \"firstValue\",\n    \"secondParameter\": \"secondValue\"\n}"

  private val testRequest: TestRequest = TestRequest("firstValue", "secondValue")
}
