package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates the advance booking number, shipper load /count and packing list details. This details must be provided by the user during
  * freight shipment.
  *
  * @param bookingConfirmationNumber
  *   This is an advanced booking number that must be acquired through the appropriate channel in the shipment origin country. Without the
  *   booking number pickup and space allocation for the Express Freight shipment are not guaranteed. <br>Minimum length: 5 digits <br>
  *   Maximum length: 12 digits <br>Example: XXXX56789812
  * @param shippersLoadAndCount
  *   Indicates the content of a container were loaded and counted by the shipper.<br>Minimum length: 1 digits <br> Maximum length: 5 digits
  *   <br>Example: If a skid has 32 small boxes on it that are shrinkwrapped, the shippersLoadAndCount should be “32”
  * @param packingListEnclosed
  *   This indicates whether or not the Packing List is enclosed with the shipment. A packing list is a document that includes details about
  *   the contents of a package. <br> Example: true
  */
case class ExpressFreightDetail(
    bookingConfirmationNumber: Option[String] = None,
    shippersLoadAndCount: Option[Int] = None,
    packingListEnclosed: Option[Boolean] = None
)

object ExpressFreightDetail {

  given Encoder[ExpressFreightDetail] = new Encoder.AsObject[ExpressFreightDetail] {
    final def encodeObject(o: ExpressFreightDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "bookingConfirmationNumber" -> o.bookingConfirmationNumber.asJson,
          "shippersLoadAndCount"      -> o.shippersLoadAndCount.asJson,
          "packingListEnclosed"       -> o.packingListEnclosed.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ExpressFreightDetail] = (c: HCursor) => {
    for {
      bookingConfirmationNumber <- c.downField("bookingConfirmationNumber").as[Option[String]]
      shippersLoadAndCount      <- c.downField("shippersLoadAndCount").as[Option[Int]]
      packingListEnclosed       <- c.downField("packingListEnclosed").as[Option[Boolean]]
    } yield ExpressFreightDetail(bookingConfirmationNumber, shippersLoadAndCount, packingListEnclosed)
  }
}
