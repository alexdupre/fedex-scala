package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates the USMCA detail
  *
  * @param originCriterion
  *   Specify the origin criterion.
  */
case class UsmcaDetail(
    originCriterion: Option[UsmcaDetail.OriginCriterion] = None
)

object UsmcaDetail {
  enum OriginCriterion {
    case A
    case B
    case C
    case D
    case E
  }
  object OriginCriterion {
    given Encoder[OriginCriterion] = Encoder.encodeString.contramap(_.toString)
    given Decoder[OriginCriterion] = Decoder.decodeString.emapTry(s => scala.util.Try(OriginCriterion.valueOf(s)))
  }
  given Encoder[UsmcaDetail] = new Encoder.AsObject[UsmcaDetail] {
    final def encodeObject(o: UsmcaDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "originCriterion" -> o.originCriterion.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[UsmcaDetail] = (c: HCursor) => {
    for {
      originCriterion <- c.downField("originCriterion").as[Option[OriginCriterion]]
    } yield UsmcaDetail(originCriterion)
  }
}
