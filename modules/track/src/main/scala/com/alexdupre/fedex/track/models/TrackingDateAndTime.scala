package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Date and time information for the tracked shipment. Date and time information for the tracked shipment includes various type of date
  * time including when the package was shipped, when it is expected to deliver, when it is actually delivered etc.
  *
  * @param dateTime
  *   Field which holds the tracking date or timestamp in ISO format.<br>Format: YYYY-MM-DD<br> Example: '2019-05-07'
  * @param `type`
  *   Field which holds information about the type of tracking date or timestamp.<br> Example: 'ACTUAL_DELIVERY'
  */
case class TrackingDateAndTime(
    dateTime: Option[String] = None,
    `type`: Option[TrackingDateAndTime.Type] = None
)

object TrackingDateAndTime {
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
    case UNKNOWN_DEFAULT
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.map(s => scala.util.Try(Type.valueOf(s)).getOrElse(Type.UNKNOWN_DEFAULT))
  }
  given Encoder[TrackingDateAndTime] = new Encoder.AsObject[TrackingDateAndTime] {
    final def encodeObject(o: TrackingDateAndTime): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "dateTime" -> o.dateTime.asJson,
          "type"     -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingDateAndTime] = (c: HCursor) => {
    for {
      dateTime <- c.downField("dateTime").as[Option[String]]
      `type`   <- c.downField("type").as[Option[Type]]
    } yield TrackingDateAndTime(dateTime, `type`)
  }
}
