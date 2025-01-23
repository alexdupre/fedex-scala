package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** TimeWindow Model
  *
  * @param description
  *   Field which describes the time window provided. <br> Example: Description field
  * @param `type`
  *   Field which holds the code representing the description for the time window provided. <br> Example: ESTIMATED_DELIVERY
  */
case class TimeWindow(
    description: Option[String] = None,
    window: Option[TimeRange] = None,
    `type`: Option[TimeWindow.Type] = None
)

object TimeWindow {
  enum Type {
    case ACTUAL_DELIVERY
    case ACTUAL_PICKUP
    case ACTUAL_TENDER
    case ANTICIPATED_TENDER
    case APPOINTMENT_DELIVERY
    case ATTEMPTED_DELIVERY
    case COMMITMENT
    case ESTIMATED_ARRIVAL_AT_GATEWAY
    case ESTIMATED_DELIVERY
    case ESTIMATED_PICKUP
    case ESTIMATED_RETURN_TO_STATION
    case SHIP
    case SHIPMENT_DATA_RECEIVED
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.emapTry(s => scala.util.Try(Type.valueOf(s)))
  }
  given Encoder[TimeWindow] = new Encoder.AsObject[TimeWindow] {
    final def encodeObject(o: TimeWindow): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "description" -> o.description.asJson,
          "window"      -> o.window.asJson,
          "type"        -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TimeWindow] = (c: HCursor) => {
    for {
      description <- c.downField("description").as[Option[String]]
      window      <- c.downField("window").as[Option[TimeRange]]
      `type`      <- c.downField("type").as[Option[Type]]
    } yield TimeWindow(description, window, `type`)
  }
}
