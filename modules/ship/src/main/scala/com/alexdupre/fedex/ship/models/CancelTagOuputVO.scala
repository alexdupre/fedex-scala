package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The output details when a tag is cancelled.
  *
  * @param cancelledTag
  *   Indicates whether the tag has been cancelled or not. If true, then the tag has been successfully cancelled.<br>Example: true
  * @param successMessage
  *   Message received when a tag is successfully cancelled.<br>Example: success
  */
case class CancelTagOuputVO(
    cancelledTag: Option[Boolean] = None,
    successMessage: Option[String] = None
)

object CancelTagOuputVO {

  given Encoder[CancelTagOuputVO] = new Encoder.AsObject[CancelTagOuputVO] {
    final def encodeObject(o: CancelTagOuputVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "cancelledTag"   -> o.cancelledTag.asJson,
          "successMessage" -> o.successMessage.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CancelTagOuputVO] = (c: HCursor) => {
    for {
      cancelledTag   <- c.downField("cancelledTag").as[Option[Boolean]]
      successMessage <- c.downField("successMessage").as[Option[String]]
    } yield CancelTagOuputVO(cancelledTag, successMessage)
  }
}
