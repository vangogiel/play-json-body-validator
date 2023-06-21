package play.api.mvc

object PlayJsonValidationErrors {
  val PathMissing = "error.path.missing"
  val FieldHasInvalidValue = "error.invalidValue"
  val FieldEmpty = "error.required"
  val MultipleResults = "error.path.result.multiple"
  val ExpectedJsArray = "error.expected.jsarray"
}
