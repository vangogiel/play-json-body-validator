package play.api.mvc

object PlayJsonValidationErrors {
  val PathMissing = "error.path.missing"
  val FieldHasInvalidValue = "error.invalidValue"
  val FieldEmpty = "error.required"
  val MultipleResults = "error.path.result.multiple"
  val ExpectedJsArray = "error.expected.jsarray"
  val ExpectedInt = "error.expected.int"
  val ExpectedShort = "error.expected.short"
  val ExpectedByte = "error.expected.byte"
  val ExpectedLong = "error.expected.long"
  val ExpectedNumber = "error.expected.jsnumber"
  val ExpectedBigDecimal = "error.expected.jsnumberorjsstring"
  val ExpectedBigInteger = "error.invalid.biginteger"
  val ExpectedValidEnumValues = "error.expected.validenumvalue"
  val ExpectedEnumString = "error.expected.enumstring"
  val ExpectedBoolean = "error.expected.jsboolean"
}
