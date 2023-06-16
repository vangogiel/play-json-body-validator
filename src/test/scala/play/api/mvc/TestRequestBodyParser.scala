package play.api.mvc

import akka.actor.ActorSystem
import akka.util.ByteString
import org.scalatest.concurrent.Eventually
import org.scalatestplus.play.PlaySpec
import play.api.http.Status.BAD_REQUEST
import play.api.test.FakeRequest
import play.api.test.Helpers.{ contentAsJson, defaultAwaitTimeout, status, stubPlayBodyParsers }

import scala.concurrent.ExecutionContext.Implicits.global

class TestRequestBodyParser extends PlaySpec with Eventually {
  implicit val actorSystem: ActorSystem = ActorSystem()

  val bodyParser = new TestBodyParser(stubPlayBodyParsers)

  "Parsing a request body" when {
    "the body is valid and the request" should {
      "return a valid json value" in {
        val outcome = bodyParser
          .parseRequest()
          .apply(FakeRequest())
          .run(ByteString(mockJson))
          .map(_.toOption.get)

        eventually {
          (outcome.value.get.get \ "firstParameter").as[String] mustBe "firstValue"
          (outcome.value.get.get \ "secondParameter").as[String] mustBe "secondValue"
        }
      }
    }

    "the body is empty" should {
      val outcome = bodyParser
        .parseRequest()
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
  }

  private val mockJson: String =
    "{\n    \"firstParameter\": \"firstValue\",\n    \"secondParameter\": \"secondValue\"\n}"
}
