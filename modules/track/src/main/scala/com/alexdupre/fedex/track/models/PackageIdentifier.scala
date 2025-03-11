package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The type and value of the package identifier that is to be used to retrieve the tracking information for a package or group of packages.
  *
  * @param `type`
  *   Indicate the package identifier to identify the package.<br> Example: SHIPPER_REFERENCE
  * @param value
  *   Field which holds the value of the identifier used to identify the package. <br>Example: 'ASJFGVAS'
  * @param trackingNumberUniqueId
  *   Unique identifier used to distinguish duplicate FedEx tracking numbers. This value will be set by FedEx systems. <br> Example:
  *   245822\~123456789012\~FDEG
  */
case class PackageIdentifier(
    `type`: Option[PackageIdentifier.Type] = None,
    value: Option[Seq[String]] = None,
    trackingNumberUniqueId: Option[String] = None
)

object PackageIdentifier {
  enum Type {
    case BILL_OF_LADING
    case COD_RETURN_TRACKING_NUMBER
    case CUSTOMER_AUTHORIZATION_NUMBER
    case CUSTOMER_REFERENCE
    case DEPARTMENT
    case DOCUMENT_AIRWAY_BILL
    case EXPRESS_ALTERNATE_REFERENCE
    case FEDEX_OFFICE_JOB_ORDER_NUMBER
    case FREE_FORM_REFERENCE
    case GROUND_INTERNATIONAL
    case GROUND_SHIPMENT_ID
    case GROUP_MPS
    case INTERNATIONAL_DISTRIBUTION
    case INVOICE
    case JOB_GLOBAL_TRACKING_NUMBER
    case ORDER_GLOBAL_TRACKING_NUMBER
    case ORDER_TO_PAY_NUMBER
    case OUTBOUND_LINK_TO_RETURN
    case PART_NUMBER
    case PARTNER_CARRIER_NUMBER
    case PURCHASE_ORDER
    case REROUTE_TRACKING_NUMBER
    case RETURN_MATERIALS_AUTHORIZATION
    case RETURNED_TO_SHIPPER_TRACKING_NUMBER
    case SHIPPER_REFERENCE
    case STANDARD_MPS
    case TRACKING_CONTROL_NUMBER
    case TRACKING_NUMBER_OR_DOORTAG
    case TRANSBORDER_DISTRIBUTION
    case TRANSPORTATION_CONTROL_NUMBER
    case VIRTUAL_CONSOLIDATION
    case UNKNOWN_DEFAULT
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.map(s => scala.util.Try(Type.valueOf(s)).getOrElse(Type.UNKNOWN_DEFAULT))
  }
  given Encoder[PackageIdentifier] = new Encoder.AsObject[PackageIdentifier] {
    final def encodeObject(o: PackageIdentifier): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "type"                   -> o.`type`.asJson,
          "value"                  -> o.value.asJson,
          "trackingNumberUniqueId" -> o.trackingNumberUniqueId.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PackageIdentifier] = (c: HCursor) => {
    for {
      `type`                 <- c.downField("type").as[Option[Type]]
      value                  <- c.downField("value").as[Option[Seq[String]]]
      trackingNumberUniqueId <- c.downField("trackingNumberUniqueId").as[Option[String]]
    } yield PackageIdentifier(`type`, value, trackingNumberUniqueId)
  }
}
