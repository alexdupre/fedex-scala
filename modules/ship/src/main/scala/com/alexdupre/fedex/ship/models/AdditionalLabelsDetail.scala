package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** AdditionalLabelsDetail Model
  *
  * @param `type`
  *   Specify the type of additional details to be added on the label.<br>Example:MANIFEST
  * @param count
  *   Specifies the count of label to return.<br>Example:1
  */
case class AdditionalLabelsDetail(
    `type`: Option[AdditionalLabelsDetail.Type] = None,
    count: Option[Int] = None
)

object AdditionalLabelsDetail {
  enum Type {
    case BROKER
    case CONSIGNEE
    case CUSTOMS
    case DESTINATION
    case DESTINATION_CONTROL_STATEMENT
    case FREIGHT_REFERENCE
    case MANIFEST
    case ORIGIN
    case RECIPIENT
    case SECOND_ADDRESS
    case SHIPPER
    case UNKNOWN_DEFAULT
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.map(s => scala.util.Try(Type.valueOf(s)).getOrElse(Type.UNKNOWN_DEFAULT))
  }
  given Encoder[AdditionalLabelsDetail] = new Encoder.AsObject[AdditionalLabelsDetail] {
    final def encodeObject(o: AdditionalLabelsDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "type"  -> o.`type`.asJson,
          "count" -> o.count.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[AdditionalLabelsDetail] = (c: HCursor) => {
    for {
      `type` <- c.downField("type").as[Option[Type]]
      count  <- c.downField("count").as[Option[Int]]
    } yield AdditionalLabelsDetail(`type`, count)
  }
}
