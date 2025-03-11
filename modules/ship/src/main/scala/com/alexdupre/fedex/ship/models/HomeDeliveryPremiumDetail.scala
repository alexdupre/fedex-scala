package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are Special service elements for FedEx Ground Home Delivery shipments. If selected, element homedeliveryPremiumType is mandatory.
  *
  * @param deliveryDate
  *   This is delivery date. Required for FedEx Date Certain Home Delivery. Valid dates are Monday to Sunday. <br>There may be a delay in
  *   delivery on Sundays to locations that are geographically difficult to access.<br>Example: 2019-06-26
  * @param homedeliveryPremiumType
  *   Home Delivery Premium Type. Allows the user to specify additional premimum service options for their home delivery shipment. Customer
  *   can specify Evening delivery or a Date certain, or can specify they would like to make an appointment for the delivery.<br>Example:
  *   APPOINTMENT
  */
case class HomeDeliveryPremiumDetail(
    phoneNumber: Option[PhoneNumber1] = None,
    deliveryDate: Option[String] = None,
    homedeliveryPremiumType: Option[HomeDeliveryPremiumDetail.HomedeliveryPremiumType] = None
)

object HomeDeliveryPremiumDetail {
  enum HomedeliveryPremiumType {
    case APPOINTMENT
    case DATE_CERTAIN
    case EVENING
    case UNKNOWN_DEFAULT
  }
  object HomedeliveryPremiumType {
    given Encoder[HomedeliveryPremiumType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[HomedeliveryPremiumType] =
      Decoder.decodeString.map(s => scala.util.Try(HomedeliveryPremiumType.valueOf(s)).getOrElse(HomedeliveryPremiumType.UNKNOWN_DEFAULT))
  }
  given Encoder[HomeDeliveryPremiumDetail] = new Encoder.AsObject[HomeDeliveryPremiumDetail] {
    final def encodeObject(o: HomeDeliveryPremiumDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "phoneNumber"             -> o.phoneNumber.asJson,
          "deliveryDate"            -> o.deliveryDate.asJson,
          "homedeliveryPremiumType" -> o.homedeliveryPremiumType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[HomeDeliveryPremiumDetail] = (c: HCursor) => {
    for {
      phoneNumber             <- c.downField("phoneNumber").as[Option[PhoneNumber1]]
      deliveryDate            <- c.downField("deliveryDate").as[Option[String]]
      homedeliveryPremiumType <- c.downField("homedeliveryPremiumType").as[Option[HomedeliveryPremiumType]]
    } yield HomeDeliveryPremiumDetail(phoneNumber, deliveryDate, homedeliveryPremiumType)
  }
}
