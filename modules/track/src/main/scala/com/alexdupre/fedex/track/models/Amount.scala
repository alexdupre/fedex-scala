package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Amount Model
  *
  * @param currency
  *   Indicate the currency code.<br> Example: USD<br><a onclick='loadDocReference("countrycodes")'>Click here to see Currency Codes</a>
  * @param value
  *   Field which holds the amount value. <br> Example: 56.80
  */
case class Amount(
    currency: Option[String] = None,
    value: Option[Double] = None
)

object Amount {

  given Encoder[Amount] = new Encoder.AsObject[Amount] {
    final def encodeObject(o: Amount): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "currency" -> o.currency.asJson,
          "value"    -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Amount] = (c: HCursor) => {
    for {
      currency <- c.downField("currency").as[Option[String]]
      value    <- c.downField("value").as[Option[Double]]
    } yield Amount(currency, value)
  }
}
