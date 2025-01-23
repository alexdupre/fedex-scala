package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are surcharges details.<br><a onclick='loadDocReference("surcharges")'>click here to see Surcharges</a>
  *
  * @param amount
  *   This is the surcharge amount.<br>Example: 15.35
  * @param surchargeType
  *   This is the surcharge type.<br>Example: APPOINTMENT_DELIVERY
  * @param level
  *   Specifies if the surcharge applies to the entire shipment, or to an individual package.<br>Example: PACKAGE
  * @param description
  *   Specifies the description of the surcharge. Indicates delivery and returns information for FedEx Ground Economy services. <br>Example:
  *   Fuel Surcharge
  */
case class Surcharge(
    amount: Option[Double] = None,
    surchargeType: Option[String] = None,
    level: Option[String] = None,
    description: Option[String] = None
)

object Surcharge {

  given Encoder[Surcharge] = new Encoder.AsObject[Surcharge] {
    final def encodeObject(o: Surcharge): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "amount"        -> o.amount.asJson,
          "surchargeType" -> o.surchargeType.asJson,
          "level"         -> o.level.asJson,
          "description"   -> o.description.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Surcharge] = (c: HCursor) => {
    for {
      amount        <- c.downField("amount").as[Option[Double]]
      surchargeType <- c.downField("surchargeType").as[Option[String]]
      level         <- c.downField("level").as[Option[String]]
      description   <- c.downField("description").as[Option[String]]
    } yield Surcharge(amount, surchargeType, level, description)
  }
}
