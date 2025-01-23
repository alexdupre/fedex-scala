package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Field which holds information about contents of the shipment. Populated for secure users only.
  *
  * @param itemNumber
  *   Field holds the item number for the contents of shipment. <br> Example: RZ5678
  * @param receivedQuantity
  *   Field which holds information about the quantity received. <br> Example: 13
  * @param description
  *   Field which holds informative description about shipment content. <br> Example: pulyurethane rope
  * @param partNumber
  *   Holds the part number of the content in shipment. <br> Example: RK1345
  */
case class ShipmentContent(
    itemNumber: Option[String] = None,
    receivedQuantity: Option[String] = None,
    description: Option[String] = None,
    partNumber: Option[String] = None
)

object ShipmentContent {

  given Encoder[ShipmentContent] = new Encoder.AsObject[ShipmentContent] {
    final def encodeObject(o: ShipmentContent): JsonObject = {
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
  given Decoder[ShipmentContent] = (c: HCursor) => {
    for {
      itemNumber       <- c.downField("itemNumber").as[Option[String]]
      receivedQuantity <- c.downField("receivedQuantity").as[Option[String]]
      description      <- c.downField("description").as[Option[String]]
      partNumber       <- c.downField("partNumber").as[Option[String]]
    } yield ShipmentContent(itemNumber, receivedQuantity, description, partNumber)
  }
}
