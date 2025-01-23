package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This customs value is applicable for all items(or units) under the specified commodity
  *
  * @param amount
  *   This is the amount. Maximum limit is 5 digits before decimal.<br>Example: 12.45
  * @param currency
  *   This is the currency code for the amount.<br>Example: USD<br><a onclick='loadDocReference("currencycodes")'>Click here to see Currency
  *   codes</a>
  */
case class Money(
    amount: Option[Double] = None,
    currency: Option[String] = None
)

object Money {

  given Encoder[Money] = new Encoder.AsObject[Money] {
    final def encodeObject(o: Money): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "amount"   -> o.amount.asJson,
          "currency" -> o.currency.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Money] = (c: HCursor) => {
    for {
      amount   <- c.downField("amount").as[Option[Double]]
      currency <- c.downField("currency").as[Option[String]]
    } yield Money(amount, currency)
  }
}
