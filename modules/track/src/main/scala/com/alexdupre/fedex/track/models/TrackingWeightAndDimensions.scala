package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Field which holds the weight and dimension information.
  *
  * @param weight
  *   Field which holds the weight of the package.
  * @param dimensions
  *   Indicate the dimensions of the package.<br> Following conditions will apply: <ul><li>Dimensions are optional but when added, then all
  *   three dimensions must be indicated.</li><li>Dimensions are required with YOUR_PACKAGING package type.</li></ul>Note: The
  *   maximum/minimum dimension values varies based on the services and the packaging types. Refer <a
  *   href="https://www.fedex.com/en-us/service-guide.html#" target="_blank">FedEx Service Guide</a> for service details related to DIM
  *   Weighting for FedEx Express and oversize conditions for FedEx Express and FedEx Ground.
  */
case class TrackingWeightAndDimensions(
    weight: Option[Seq[Weight]] = None,
    dimensions: Option[Seq[Dimensions]] = None
)

object TrackingWeightAndDimensions {

  given Encoder[TrackingWeightAndDimensions] = new Encoder.AsObject[TrackingWeightAndDimensions] {
    final def encodeObject(o: TrackingWeightAndDimensions): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "weight"     -> o.weight.asJson,
          "dimensions" -> o.dimensions.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingWeightAndDimensions] = (c: HCursor) => {
    for {
      weight     <- c.downField("weight").as[Option[Seq[Weight]]]
      dimensions <- c.downField("dimensions").as[Option[Seq[Dimensions]]]
    } yield TrackingWeightAndDimensions(weight, dimensions)
  }
}
