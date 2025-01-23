package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The request elements required to cancel a shipment.
  *
  * @param accountNumber
  *   The account number (account value) associated with the shipment.
  * @param trackingNumber
  *   This is an unique number assigned by FedEx to the packages for tracking.<br>Example: "794953555571"
  * @param emailShipment
  *   A boolean flag passed by Clients to indicate that whether a shipment is a EMAIL shipment(Pending Shipment) or not. Once a shipment is
  *   confirmed, it can no longer be cancelled by having this flag as True.
  * @param senderCountryCode
  *   The two-letter sender Country code(Ex: US, CA, GB..etc).<br>Example: US<br><a onclick='loadDocReference("countrycodes")'>Click here to
  *   see Country Codes</a>
  * @param deletionControl
  *   Specifies which packages in a shipment to be canceled<b>DELETE_ALL_PACKAGES</b> which will cancel all tracking numbers associated to
  *   the shipment.
  */
case class FullSchemaCancelShipment(
    accountNumber: ShipperAccountNumber,
    trackingNumber: String,
    emailShipment: Option[Boolean] = None,
    senderCountryCode: Option[String] = None,
    deletionControl: Option[FullSchemaCancelShipment.DeletionControl] = None
)

object FullSchemaCancelShipment {
  enum DeletionControl {
    case DELETE_ALL_PACKAGES
  }
  object DeletionControl {
    given Encoder[DeletionControl] = Encoder.encodeString.contramap(_.toString)
    given Decoder[DeletionControl] = Decoder.decodeString.emapTry(s => scala.util.Try(DeletionControl.valueOf(s)))
  }
  given Encoder[FullSchemaCancelShipment] = new Encoder.AsObject[FullSchemaCancelShipment] {
    final def encodeObject(o: FullSchemaCancelShipment): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "accountNumber"     -> o.accountNumber.asJson,
          "trackingNumber"    -> o.trackingNumber.asJson,
          "emailShipment"     -> o.emailShipment.asJson,
          "senderCountryCode" -> o.senderCountryCode.asJson,
          "deletionControl"   -> o.deletionControl.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaCancelShipment] = (c: HCursor) => {
    for {
      accountNumber     <- c.downField("accountNumber").as[ShipperAccountNumber]
      trackingNumber    <- c.downField("trackingNumber").as[String]
      emailShipment     <- c.downField("emailShipment").as[Option[Boolean]]
      senderCountryCode <- c.downField("senderCountryCode").as[Option[String]]
      deletionControl   <- c.downField("deletionControl").as[Option[DeletionControl]]
    } yield FullSchemaCancelShipment(accountNumber, trackingNumber, emailShipment, senderCountryCode, deletionControl)
  }
}
