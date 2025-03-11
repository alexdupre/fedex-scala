package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to specify the smartpost shipment details. <br>Required for SMARTPOST service. If SmartPostInfoDetail is indicated, the
  * elements below it are also required.
  *
  * @param hubId
  *   Required<br>Specify the HubID using the four-digit numeric value.<br>Example: 5015
  * @param indicia
  *   Specify the indicia type.<br>Available options include:<ul><li>MEDIA_MAIL</li><li>PARCEL_SELECT (1 LB through 70
  *   LB)</li><li>PRESORTED_BOUND_PRINTED_MATTER</li><li>PRESORTED_STANDARD (less than 1
  *   LB)</li><li>PARCEL_RETURN</li></ul>Example:PRESORTED_STANDARD
  * @param ancillaryEndorsement
  *   Required for Presorted Standard but not for returns or parcel select. They are not all usable for all ancillary
  *   endorsements.<br>Example: RETURN_SERVICE
  * @param specialServices
  *   SmartPost Shipment Special Service Type<br>Example: USPS_DELIVERY_CONFIRMATION
  */
case class SmartPostInfoDetail(
    hubId: String,
    indicia: SmartPostInfoDetail.Indicia,
    ancillaryEndorsement: Option[SmartPostInfoDetail.AncillaryEndorsement] = None,
    specialServices: Option[SmartPostInfoDetail.SpecialServices] = None
)

object SmartPostInfoDetail {
  enum AncillaryEndorsement {
    case ADDRESS_CORRECTION
    case CARRIER_LEAVE_IF_NO_RESPONSE
    case CHANGE_SERVICE
    case FORWARDING_SERVICE
    case RETURN_SERVICE
    case UNKNOWN_DEFAULT
  }
  object AncillaryEndorsement {
    given Encoder[AncillaryEndorsement] = Encoder.encodeString.contramap(_.toString)
    given Decoder[AncillaryEndorsement] =
      Decoder.decodeString.map(s => scala.util.Try(AncillaryEndorsement.valueOf(s)).getOrElse(AncillaryEndorsement.UNKNOWN_DEFAULT))
  }

  enum Indicia {
    case MEDIA_MAIL
    case PARCEL_RETURN
    case PARCEL_SELECT
    case PRESORTED_BOUND_PRINTED_MATTER
    case PRESORTED_STANDARD
    case UNKNOWN_DEFAULT
  }
  object Indicia {
    given Encoder[Indicia] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Indicia] = Decoder.decodeString.map(s => scala.util.Try(Indicia.valueOf(s)).getOrElse(Indicia.UNKNOWN_DEFAULT))
  }

  enum SpecialServices {
    case USPS_DELIVERY_CONFIRMATION
    case UNKNOWN_DEFAULT
  }
  object SpecialServices {
    given Encoder[SpecialServices] = Encoder.encodeString.contramap(_.toString)
    given Decoder[SpecialServices] =
      Decoder.decodeString.map(s => scala.util.Try(SpecialServices.valueOf(s)).getOrElse(SpecialServices.UNKNOWN_DEFAULT))
  }
  given Encoder[SmartPostInfoDetail] = new Encoder.AsObject[SmartPostInfoDetail] {
    final def encodeObject(o: SmartPostInfoDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "hubId"                -> o.hubId.asJson,
          "indicia"              -> o.indicia.asJson,
          "ancillaryEndorsement" -> o.ancillaryEndorsement.asJson,
          "specialServices"      -> o.specialServices.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[SmartPostInfoDetail] = (c: HCursor) => {
    for {
      hubId                <- c.downField("hubId").as[String]
      indicia              <- c.downField("indicia").as[Indicia]
      ancillaryEndorsement <- c.downField("ancillaryEndorsement").as[Option[AncillaryEndorsement]]
      specialServices      <- c.downField("specialServices").as[Option[SpecialServices]]
    } yield SmartPostInfoDetail(hubId, indicia, ancillaryEndorsement, specialServices)
  }
}
