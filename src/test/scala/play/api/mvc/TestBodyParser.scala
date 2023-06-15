package play.api.mvc

import scala.concurrent.ExecutionContext

class TestBodyParser(parser: PlayBodyParsers)(implicit override val ec: ExecutionContext)
    extends RequestBodyParser(parser) {}
