package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are broker details for the shipment.
  *
  * @param broker
  *   These are broker details for the shipment with physical address, contact and account number information.
  * @param `type`
  *   Identifies the type of Broker.<br>Example: IMPORT
  */
case class BrokerDetail(
    broker: Option[Party1] = None,
    `type`: Option[BrokerDetail.Type] = None
)

object BrokerDetail {
  enum Type {
    case IMPORT
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.emapTry(s => scala.util.Try(Type.valueOf(s)))
  }
  given Encoder[BrokerDetail] = new Encoder.AsObject[BrokerDetail] {
    final def encodeObject(o: BrokerDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "broker" -> o.broker.asJson,
          "type"   -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[BrokerDetail] = (c: HCursor) => {
    for {
      broker <- c.downField("broker").as[Option[Party1]]
      `type` <- c.downField("type").as[Option[Type]]
    } yield BrokerDetail(broker, `type`)
  }
}
