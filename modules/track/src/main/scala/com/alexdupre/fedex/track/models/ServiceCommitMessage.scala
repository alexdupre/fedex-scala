package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Commitment message for this package. Informative messages related to the package. Used to convey information such as FedEx has received
  * information about a package but has not yet taken possession of it. FedEx has handed the package off to a third party for final
  * delivery. The package delivery has been cancelled.
  *
  * @param message
  *   Field which holds the commitment message for this package.<br> Example: No scheduled delivery date available at this time.
  * @param `type`
  *   Field which holds the type of service commit message.<br> Example: ESTIMATED_DELIVERY_DATE_UNAVAILABLE
  */
case class ServiceCommitMessage(
    message: Option[String] = None,
    `type`: Option[ServiceCommitMessage.Type] = None
)

object ServiceCommitMessage {
  enum Type {
    case BROKER_DELIVERED_DESCRIPTION
    case CANCELLED_DESCRIPTION
    case DELIVERY_IN_MULTIPLE_PIECE_SHIPMENT
    case ESTIMATED_DELIVERY_DATE_UNAVAILABLE
    case EXCEPTION_IN_MULTIPLE_PIECE_SHIPMENT
    case FINAL_DELIVERY_ATTEMPTED
    case FIRST_DELIVERY_ATTEMPTED
    case HELD_PACKAGE_AVAILABLE_FOR_RECIPIENT_PICKUP
    case HELD_PACKAGE_AVAILABLE_FOR_RECIPIENT_PICKUP_WITH_ADDRESS
    case HELD_PACKAGE_NOT_AVAILABLE_FOR_RECIPIENT_PICKUP
    case SHIPMENT_LABEL_CREATED
    case SUBSEQUENT_DELIVERY_ATTEMPTED
    case USPS_DELIVERED
    case `USPS_DELIVERING"`
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.emapTry(s => scala.util.Try(Type.valueOf(s)))
  }
  given Encoder[ServiceCommitMessage] = new Encoder.AsObject[ServiceCommitMessage] {
    final def encodeObject(o: ServiceCommitMessage): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "message" -> o.message.asJson,
          "type"    -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ServiceCommitMessage] = (c: HCursor) => {
    for {
      message <- c.downField("message").as[Option[String]]
      `type`  <- c.downField("type").as[Option[Type]]
    } yield ServiceCommitMessage(message, `type`)
  }
}
