package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** CompletedTagDetail_2 Model
  *
  * @param confirmationNumber
  *   Confirmation Number.<br>Example: 275
  * @param location
  *   Applicable for FedEx Express services.<br>Example: NQAA
  * @param dispatchDate
  *   The dispatch date for the FedEx Tag to be cancelled.<br>Example: 2019-08-03
  */
case class CompletedTagDetail2(
    confirmationNumber: String,
    location: String,
    dispatchDate: String
)

object CompletedTagDetail2 {

  given Encoder[CompletedTagDetail2] = new Encoder.AsObject[CompletedTagDetail2] {
    final def encodeObject(o: CompletedTagDetail2): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "confirmationNumber" -> o.confirmationNumber.asJson,
          "location"           -> o.location.asJson,
          "dispatchDate"       -> o.dispatchDate.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CompletedTagDetail2] = (c: HCursor) => {
    for {
      confirmationNumber <- c.downField("confirmationNumber").as[String]
      location           <- c.downField("location").as[String]
      dispatchDate       <- c.downField("dispatchDate").as[String]
    } yield CompletedTagDetail2(confirmationNumber, location, dispatchDate)
  }
}
