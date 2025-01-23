package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This customs value is applicable for all items(or units) under the specified commodity.
  *
  * @param amount
  *   This is commodity value in amount used for Customs declaration.<br>Max limit: 11 digits before decimal.<br>Example: 1,55,6457.25
  * @param currency
  *   This is the currency code for the amount.<br>Example: USD<br><a onclick='loadDocReference("currencycodes")'>Click here to see Currency
  *   codes</a>
  */
case class CustomsMoney(
    amount: Option[Double] = None,
    currency: Option[String] = None
)

object CustomsMoney {

  given Encoder[CustomsMoney] = new Encoder.AsObject[CustomsMoney] {
    final def encodeObject(o: CustomsMoney): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "amount"   -> o.amount.asJson,
          "currency" -> o.currency.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CustomsMoney] = (c: HCursor) => {
    for {
      amount   <- c.downField("amount").as[Option[Double]]
      currency <- c.downField("currency").as[Option[String]]
    } yield CustomsMoney(amount, currency)
  }
}
