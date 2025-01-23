package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specify types of special handlings that are applied to this package.<br><a
  * onclick='loadDocReference("fedexexpressspecialhandlingcodes")'>Click here to see FedEx Express Special Handling Codes.</a>
  *
  * @param description
  *   Field which holds the text description of the special handling code.<br> Example: Deliver Weekday
  * @param `type`
  *   Field which holds type representing the special handling.<br> Example: DELIVER_WEEKDAY
  * @param paymentType
  *   Field which holds information about the payment handling related to the special handling.<br> Example: OTHER
  */
case class TrackSpecialHandling(
    description: Option[String] = None,
    `type`: Option[String] = None,
    paymentType: Option[String] = None
)

object TrackSpecialHandling {

  given Encoder[TrackSpecialHandling] = new Encoder.AsObject[TrackSpecialHandling] {
    final def encodeObject(o: TrackSpecialHandling): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "description" -> o.description.asJson,
          "type"        -> o.`type`.asJson,
          "paymentType" -> o.paymentType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackSpecialHandling] = (c: HCursor) => {
    for {
      description <- c.downField("description").as[Option[String]]
      `type`      <- c.downField("type").as[Option[String]]
      paymentType <- c.downField("paymentType").as[Option[String]]
    } yield TrackSpecialHandling(description, `type`, paymentType)
  }
}
