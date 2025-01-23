package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are COD package details. For use with FedEx Ground services only; COD must be present in shipments special services.
  *
  * @param codCollectionAmount
  *   Indicate the COD collection amount.
  */
case class PackageCODDetail(
    codCollectionAmount: Option[Money] = None
)

object PackageCODDetail {

  given Encoder[PackageCODDetail] = new Encoder.AsObject[PackageCODDetail] {
    final def encodeObject(o: PackageCODDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "codCollectionAmount" -> o.codCollectionAmount.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PackageCODDetail] = (c: HCursor) => {
    for {
      codCollectionAmount <- c.downField("codCollectionAmount").as[Option[Money]]
    } yield PackageCODDetail(codCollectionAmount)
  }
}
