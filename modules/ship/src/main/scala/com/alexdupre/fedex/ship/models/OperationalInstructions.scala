package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** OperationalInstructions Model
  *
  * @param number
  *   Specifies the number of operational instructions returned for this shipment.<br>Example: 17
  * @param content
  *   This is an operational instruction printed or available on the shipping document.<br>Example: SECURED
  */
case class OperationalInstructions(
    number: Option[Int] = None,
    content: Option[String] = None
)

object OperationalInstructions {

  given Encoder[OperationalInstructions] = new Encoder.AsObject[OperationalInstructions] {
    final def encodeObject(o: OperationalInstructions): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "number"  -> o.number.asJson,
          "content" -> o.content.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[OperationalInstructions] = (c: HCursor) => {
    for {
      number  <- c.downField("number").as[Option[Int]]
      content <- c.downField("content").as[Option[String]]
    } yield OperationalInstructions(number, content)
  }
}
