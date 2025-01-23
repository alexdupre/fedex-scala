package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the currency exchange performed on financial amounts on this rate.
  *
  * @param rate
  *   Multiplier used to convert from Currency units to into Currency units.<br>Example: 25.6
  * @param fromCurrency
  *   The currency code for the original (converted FROM) currency.<br>Example: Rupee<br><a
  *   onclick='loadDocReference("currencycodes")'>click here to see Currency codes</a>
  * @param intoCurrency
  *   The currency code for the final(converted INTO) currency.<br>Example: USD<br><a onclick='loadDocReference("currencycodes")'>click here
  *   to see currencycodes</a>
  */
case class CurrencyExchangeRate(
    rate: Option[Double] = None,
    fromCurrency: Option[String] = None,
    intoCurrency: Option[String] = None
)

object CurrencyExchangeRate {

  given Encoder[CurrencyExchangeRate] = new Encoder.AsObject[CurrencyExchangeRate] {
    final def encodeObject(o: CurrencyExchangeRate): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "rate"         -> o.rate.asJson,
          "fromCurrency" -> o.fromCurrency.asJson,
          "intoCurrency" -> o.intoCurrency.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CurrencyExchangeRate] = (c: HCursor) => {
    for {
      rate         <- c.downField("rate").as[Option[Double]]
      fromCurrency <- c.downField("fromCurrency").as[Option[String]]
      intoCurrency <- c.downField("intoCurrency").as[Option[String]]
    } yield CurrencyExchangeRate(rate, fromCurrency, intoCurrency)
  }
}
