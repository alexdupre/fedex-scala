package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Notifications to the end user that provide additional information relevant to the tracked shipment. For example, a notification may
  * indicate that a change in behavior has occurred.
  *
  * @param code
  *   Field which holds the code that designates the type of informational message being returned. <br>Example:
  *   'CLEARANCE_ENTRY_FEE_APPLIES'
  * @param description
  *   Field which holds the The informational message in human readable form.<br> Example: this is an informational message
  */
case class InformationNoteDetail(
    code: Option[String] = None,
    description: Option[String] = None
)

object InformationNoteDetail {

  given Encoder[InformationNoteDetail] = new Encoder.AsObject[InformationNoteDetail] {
    final def encodeObject(o: InformationNoteDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"        -> o.code.asJson,
          "description" -> o.description.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[InformationNoteDetail] = (c: HCursor) => {
    for {
      code        <- c.downField("code").as[Option[String]]
      description <- c.downField("description").as[Option[String]]
    } yield InformationNoteDetail(code, description)
  }
}
