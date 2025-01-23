package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** List of delivery options that can be used to customize the delivery of the package.
  *
  * @param description
  *   Field which specifies the description of the custom delivery option requested <br> Example: Redirect the package to the hold location.
  * @param `type`
  *   Field which specifies the type of the custom delivery option requested.<br> Example: REDIRECT_TO_HOLD_AT_LOCATION
  * @param status
  *   Field which specifies the status of the custom delivery option requested.<br> Example: HELD
  */
case class CustomDeliveryOption(
    requestedAppointmentDetail: Option[RequestedAppointmentDetail] = None,
    description: Option[String] = None,
    `type`: Option[CustomDeliveryOption.Type] = None,
    status: Option[String] = None
)

object CustomDeliveryOption {
  enum Type {
    case REROUTE
    case APPOINTMENT
    case DATE_CERTAIN
    case EVENING
    case REDIRECT_TO_HOLD_AT_LOCATION
    case ELECTRONIC_SIGNATURE_RELEASE
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.emapTry(s => scala.util.Try(Type.valueOf(s)))
  }
  given Encoder[CustomDeliveryOption] = new Encoder.AsObject[CustomDeliveryOption] {
    final def encodeObject(o: CustomDeliveryOption): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "requestedAppointmentDetail" -> o.requestedAppointmentDetail.asJson,
          "description"                -> o.description.asJson,
          "type"                       -> o.`type`.asJson,
          "status"                     -> o.status.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CustomDeliveryOption] = (c: HCursor) => {
    for {
      requestedAppointmentDetail <- c.downField("requestedAppointmentDetail").as[Option[RequestedAppointmentDetail]]
      description                <- c.downField("description").as[Option[String]]
      `type`                     <- c.downField("type").as[Option[Type]]
      status                     <- c.downField("status").as[Option[String]]
    } yield CustomDeliveryOption(requestedAppointmentDetail, description, `type`, status)
  }
}
