package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are document diposition details. Each occurrence of this class specifies a particular way in which a kind of shipping document is
  * to be produced and provided.
  *
  * @param dispositionType
  *   Values in this field specify how to create and return the document.<br>Example:CONFIRMED
  */
case class ShippingDocumentDispositionDetail(
    eMailDetail: Option[ShippingDocumentEmailDetail] = None,
    dispositionType: Option[ShippingDocumentDispositionDetail.DispositionType] = None
)

object ShippingDocumentDispositionDetail {
  enum DispositionType {
    case CONFIRMED
    case DEFERRED_QUEUED
    case DEFERRED_RETURNED
    case DEFERRED_STORED
    case EMAILED
    case QUEUED
    case RETURNED
    case STORED
    case UNKNOWN_DEFAULT
  }
  object DispositionType {
    given Encoder[DispositionType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[DispositionType] =
      Decoder.decodeString.map(s => scala.util.Try(DispositionType.valueOf(s)).getOrElse(DispositionType.UNKNOWN_DEFAULT))
  }
  given Encoder[ShippingDocumentDispositionDetail] = new Encoder.AsObject[ShippingDocumentDispositionDetail] {
    final def encodeObject(o: ShippingDocumentDispositionDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "eMailDetail"     -> o.eMailDetail.asJson,
          "dispositionType" -> o.dispositionType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShippingDocumentDispositionDetail] = (c: HCursor) => {
    for {
      eMailDetail     <- c.downField("eMailDetail").as[Option[ShippingDocumentEmailDetail]]
      dispositionType <- c.downField("dispositionType").as[Option[DispositionType]]
    } yield ShippingDocumentDispositionDetail(eMailDetail, dispositionType)
  }
}
