package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the count of the packages delivered and the count of the packages at the origin which can be used for verification purposes.
  * Populated for secure users only.
  *
  * @param count
  *   Field which holds the piece count. <br> Example: 35
  * @param description
  *   Field which holds the piece count description detail. <br> Example: picec count description
  * @param `type`
  *   Field which holds the piece count location type. <br> Example: ORIGIN
  */
case class PieceCountDetail(
    count: Option[String] = None,
    description: Option[String] = None,
    `type`: Option[PieceCountDetail.Type] = None
)

object PieceCountDetail {
  enum Type {
    case DESTINATION
    case ORIGIN
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.emapTry(s => scala.util.Try(Type.valueOf(s)))
  }
  given Encoder[PieceCountDetail] = new Encoder.AsObject[PieceCountDetail] {
    final def encodeObject(o: PieceCountDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "count"       -> o.count.asJson,
          "description" -> o.description.asJson,
          "type"        -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PieceCountDetail] = (c: HCursor) => {
    for {
      count       <- c.downField("count").as[Option[String]]
      description <- c.downField("description").as[Option[String]]
      `type`      <- c.downField("type").as[Option[Type]]
    } yield PieceCountDetail(count, description, `type`)
  }
}
