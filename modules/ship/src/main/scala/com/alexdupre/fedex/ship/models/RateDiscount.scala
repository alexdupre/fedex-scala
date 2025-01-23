package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies discount Rate for Shipment.
  *
  * @param amount
  *   Specifies the amount.<br>Example: 8.9
  * @param rateDiscountType
  *   The type of rate discount. <br/> Valid Values are BONUS, COUPON,EARNED,OTHER,VOLUME.<br>Example: COUPON
  * @param percent
  *   Specifies the percentage of Rate discount.<br>Example: 28.9
  * @param description
  *   Specifies the description of the discounted rate.<br>Example: description
  */
case class RateDiscount(
    amount: Option[Double] = None,
    rateDiscountType: Option[String] = None,
    percent: Option[Double] = None,
    description: Option[String] = None
)

object RateDiscount {

  given Encoder[RateDiscount] = new Encoder.AsObject[RateDiscount] {
    final def encodeObject(o: RateDiscount): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "amount"           -> o.amount.asJson,
          "rateDiscountType" -> o.rateDiscountType.asJson,
          "percent"          -> o.percent.asJson,
          "description"      -> o.description.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RateDiscount] = (c: HCursor) => {
    for {
      amount           <- c.downField("amount").as[Option[Double]]
      rateDiscountType <- c.downField("rateDiscountType").as[Option[String]]
      percent          <- c.downField("percent").as[Option[Double]]
      description      <- c.downField("description").as[Option[String]]
    } yield RateDiscount(amount, rateDiscountType, percent, description)
  }
}
