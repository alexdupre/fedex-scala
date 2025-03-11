package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The available tracking documents for the shipment being tracked. Available tracking documents includes SPOD and Bill of lading.
  *
  * @param size
  *   Field which holds the size of available images for the shipment being tracked. Example: LARGE
  * @param `type`
  *   Field which holds the type of available images for the shipment being tracked.<br> Example: BILL_OF_LADING
  */
case class AvailableImagesDetail(
    size: Option[AvailableImagesDetail.Size] = None,
    `type`: Option[AvailableImagesDetail.Type] = None
)

object AvailableImagesDetail {
  enum Size {
    case SMALL
    case LARGE
    case UNKNOWN_DEFAULT
  }
  object Size {
    given Encoder[Size] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Size] = Decoder.decodeString.map(s => scala.util.Try(Size.valueOf(s)).getOrElse(Size.UNKNOWN_DEFAULT))
  }

  enum Type {
    case SIGNATURE_PROOF_OF_DELIVERY
    case BILL_OF_LADING
    case UNKNOWN_DEFAULT
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.map(s => scala.util.Try(Type.valueOf(s)).getOrElse(Type.UNKNOWN_DEFAULT))
  }
  given Encoder[AvailableImagesDetail] = new Encoder.AsObject[AvailableImagesDetail] {
    final def encodeObject(o: AvailableImagesDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "size" -> o.size.asJson,
          "type" -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[AvailableImagesDetail] = (c: HCursor) => {
    for {
      size   <- c.downField("size").as[Option[Size]]
      `type` <- c.downField("type").as[Option[Type]]
    } yield AvailableImagesDetail(size, `type`)
  }
}
