package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is default holding location information when HOLD_AT_LOCATION special service is requested and the client does not specify the hold
  * location address.
  *
  * @param holdingLocationType
  *   Indicates the type of the FedEx holding location <br>Example: FEDEX_STAFFED
  * @param holdingLocation
  *   Indicate the physical address of the FedEx holding location.
  */
case class CompletedHoldAtLocationDetail(
    holdingLocationType: Option[String] = None,
    holdingLocation: Option[JustContactAndAddress] = None
)

object CompletedHoldAtLocationDetail {

  given Encoder[CompletedHoldAtLocationDetail] = new Encoder.AsObject[CompletedHoldAtLocationDetail] {
    final def encodeObject(o: CompletedHoldAtLocationDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "holdingLocationType" -> o.holdingLocationType.asJson,
          "holdingLocation"     -> o.holdingLocation.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CompletedHoldAtLocationDetail] = (c: HCursor) => {
    for {
      holdingLocationType <- c.downField("holdingLocationType").as[Option[String]]
      holdingLocation     <- c.downField("holdingLocation").as[Option[JustContactAndAddress]]
    } yield CompletedHoldAtLocationDetail(holdingLocationType, holdingLocation)
  }
}
