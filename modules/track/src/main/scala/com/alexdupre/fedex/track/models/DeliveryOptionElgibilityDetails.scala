package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies details of delivery options and its eligibility type
  *
  * @param option
  *   This is the type of delivery option.<br><br><i>Note: For eligibile DISPUTE_DELIVERY, RETURN_TO_SHIPPER, SUPPLEMENT_ADDRESS go to
  *   fedex.com to perform the option/action.</i><br><br>Example: INDIRECT_SIGNATURE_RELEASE
  * @param eligibility
  *   Eligibility of the customer for the specific delivery options.<br> Example: INELIGIBLE
  */
case class DeliveryOptionElgibilityDetails(
    option: Option[DeliveryOptionElgibilityDetails.SpecOption] = None,
    eligibility: Option[String] = None
)

object DeliveryOptionElgibilityDetails {
  enum SpecOption {
    case DISPUTE_DELIVERY
    case INDIRECT_SIGNATURE_RELEASE
    case REDIRECT_TO_HOLD_AT_LOCATION
    case REROUTE
    case RESCHEDULE
    case RETURN_TO_SHIPPER
    case SUPPLEMENT_ADDRESS
  }
  object SpecOption {
    given Encoder[SpecOption] = Encoder.encodeString.contramap(_.toString)
    given Decoder[SpecOption] = Decoder.decodeString.emapTry(s => scala.util.Try(SpecOption.valueOf(s)))
  }
  given Encoder[DeliveryOptionElgibilityDetails] = new Encoder.AsObject[DeliveryOptionElgibilityDetails] {
    final def encodeObject(o: DeliveryOptionElgibilityDetails): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "option"      -> o.option.asJson,
          "eligibility" -> o.eligibility.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DeliveryOptionElgibilityDetails] = (c: HCursor) => {
    for {
      option      <- c.downField("option").as[Option[SpecOption]]
      eligibility <- c.downField("eligibility").as[Option[String]]
    } yield DeliveryOptionElgibilityDetails(option, eligibility)
  }
}
