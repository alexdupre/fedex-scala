package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are customs Option Detail, type must be indicated for each occurrence.
  *
  * @param description
  *   Specify additional description about customs options. This is a required field when the Type is OTHER.
  * @param `type`
  *   Specify the reason for a global return, as recognized by Customs. Valid values:<ul><li>COURTESY_RETURN_LABEL: Applicable for Outbound
  *   shipments.</li><li>EXHIBITION_TRADE_SHOW: For exhibition or trade-show, outbound and inbound.</li><li>FAULTY_ITEM: For faulty item
  *   being returned, inbound only.</li><li>FOLLOWING_REPAIR: For repaired or processed item being sent, outbound only.</li><li>FOR_REPAIR:
  *   For repair or processing, outbound and inbound.</li><li>ITEM_FOR_LOAN: For loan item, outbound and inbound.</li><li>OTHER: Other
  *   reason, outbound and inbound. This type requires a description.</li><li>REJECTED: For rejected merchandise being returned,
  *   inbound.</li><li>REPLACEMENT: For replacement being sent, outbound only.</li><li>TRIAL: For use in a trial, outbound and
  *   inbound.</li></ul>
  */
case class CustomsOptionDetail(
    description: Option[String] = None,
    `type`: Option[CustomsOptionDetail.Type] = None
)

object CustomsOptionDetail {
  enum Type {
    case COURTESY_RETURN_LABEL
    case EXHIBITION_TRADE_SHOW
    case FAULTY_ITEM
    case FOLLOWING_REPAIR
    case FOR_REPAIR
    case ITEM_FOR_LOAN
    case OTHER
    case REJECTED
    case REPLACEMENT
    case TRIAL
    case UNKNOWN_DEFAULT
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.map(s => scala.util.Try(Type.valueOf(s)).getOrElse(Type.UNKNOWN_DEFAULT))
  }
  given Encoder[CustomsOptionDetail] = new Encoder.AsObject[CustomsOptionDetail] {
    final def encodeObject(o: CustomsOptionDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "description" -> o.description.asJson,
          "type"        -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CustomsOptionDetail] = (c: HCursor) => {
    for {
      description <- c.downField("description").as[Option[String]]
      `type`      <- c.downField("type").as[Option[Type]]
    } yield CustomsOptionDetail(description, `type`)
  }
}
