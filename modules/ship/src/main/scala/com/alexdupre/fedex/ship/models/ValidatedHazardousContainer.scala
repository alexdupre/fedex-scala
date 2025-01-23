package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the details of a container used to package dangerous goods commodities.
  *
  * @param qValue
  *   Indicates that the quantity of the dangerous goods packaged is permissible for shipping. This is used to ensure that the dangerous
  *   goods commodities do not exceed the net quantity per package restrictions.<br>Example: 2.0
  * @param hazardousCommodities
  *   Indicates the details of the hazardous commodities in the completed package.
  */
case class ValidatedHazardousContainer(
    qValue: Option[Double] = None,
    hazardousCommodities: Option[Seq[ValidatedHazardousCommodityContent]] = None
)

object ValidatedHazardousContainer {

  given Encoder[ValidatedHazardousContainer] = new Encoder.AsObject[ValidatedHazardousContainer] {
    final def encodeObject(o: ValidatedHazardousContainer): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "QValue"               -> o.qValue.asJson,
          "hazardousCommodities" -> o.hazardousCommodities.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ValidatedHazardousContainer] = (c: HCursor) => {
    for {
      qValue               <- c.downField("QValue").as[Option[Double]]
      hazardousCommodities <- c.downField("hazardousCommodities").as[Option[Seq[ValidatedHazardousCommodityContent]]]
    } yield ValidatedHazardousContainer(qValue, hazardousCommodities)
  }
}
