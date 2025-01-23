package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Field which holds a date/timestamp window.
  *
  * @param begins
  *   Field which holds the begin date/timestamp for a range.<br> Example: '2021-10-01T08:00:00'
  * @param ends
  *   Field which holds the end date/timestamp for a range.<br> Example: '2021-10-15T00:00:00-06:00'
  */
case class TimeRange(
    begins: Option[String] = None,
    ends: Option[String] = None
)

object TimeRange {

  given Encoder[TimeRange] = new Encoder.AsObject[TimeRange] {
    final def encodeObject(o: TimeRange): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "begins" -> o.begins.asJson,
          "ends"   -> o.ends.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TimeRange] = (c: HCursor) => {
    for {
      begins <- c.downField("begins").as[Option[String]]
      ends   <- c.downField("ends").as[Option[String]]
    } yield TimeRange(begins, ends)
  }
}
