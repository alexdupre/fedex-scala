package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to specify package content details.
  *
  * @param itemNumber
  *   This is a package item number.
  * @param receivedQuantity
  *   This is the package item quantity.
  * @param description
  *   This is the description of the package item.
  * @param partNumber
  *   This is the part number.
  */
case class ContentRecord(
    itemNumber: Option[String] = None,
    receivedQuantity: Option[Int] = None,
    description: Option[String] = None,
    partNumber: Option[String] = None
)

object ContentRecord {

  given Encoder[ContentRecord] = new Encoder.AsObject[ContentRecord] {
    final def encodeObject(o: ContentRecord): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "itemNumber"       -> o.itemNumber.asJson,
          "receivedQuantity" -> o.receivedQuantity.asJson,
          "description"      -> o.description.asJson,
          "partNumber"       -> o.partNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ContentRecord] = (c: HCursor) => {
    for {
      itemNumber       <- c.downField("itemNumber").as[Option[String]]
      receivedQuantity <- c.downField("receivedQuantity").as[Option[Int]]
      description      <- c.downField("description").as[Option[String]]
      partNumber       <- c.downField("partNumber").as[Option[String]]
    } yield ContentRecord(itemNumber, receivedQuantity, description, partNumber)
  }
}
