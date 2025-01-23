package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These the details on the kind and quantity of an individual hazardous commodity in a package.
  *
  * @param quantity
  *   Indicates hazardous commodity quantity details.
  * @param massPoints
  *   The mass points are a calculation used by ADR regulations for measuring the risk of a particular hazardous commodity.<br>Example: 2.0
  */
case class ValidatedHazardousCommodityContent(
    quantity: Option[HazardousCommodityQuantityDetail] = None,
    options: Option[HazardousCommodityOptionDetail] = None,
    description: Option[ValidatedHazardousCommodityDescription] = None,
    netExplosiveDetail: Option[NetExplosiveDetail] = None,
    massPoints: Option[Double] = None
)

object ValidatedHazardousCommodityContent {

  given Encoder[ValidatedHazardousCommodityContent] = new Encoder.AsObject[ValidatedHazardousCommodityContent] {
    final def encodeObject(o: ValidatedHazardousCommodityContent): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "quantity"           -> o.quantity.asJson,
          "options"            -> o.options.asJson,
          "description"        -> o.description.asJson,
          "netExplosiveDetail" -> o.netExplosiveDetail.asJson,
          "massPoints"         -> o.massPoints.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ValidatedHazardousCommodityContent] = (c: HCursor) => {
    for {
      quantity           <- c.downField("quantity").as[Option[HazardousCommodityQuantityDetail]]
      options            <- c.downField("options").as[Option[HazardousCommodityOptionDetail]]
      description        <- c.downField("description").as[Option[ValidatedHazardousCommodityDescription]]
      netExplosiveDetail <- c.downField("netExplosiveDetail").as[Option[NetExplosiveDetail]]
      massPoints         <- c.downField("massPoints").as[Option[Double]]
    } yield ValidatedHazardousCommodityContent(quantity, options, description, netExplosiveDetail, massPoints)
  }
}
