package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the dimensions of the package.<br> Following conditions will apply: <ul><li>Dimensions are optional but when added, then all
  * three dimensions must be indicated.</li><li>Dimensions are required with YOUR_PACKAGING package type.</li></ul>Note: The maximum/minimum
  * dimension values varies based on the services and the packaging types. Refer <a href="https://www.fedex.com/en-us/service-guide.html"
  * target="_blank">FedEx Service Guide</a> for service details related to DIM Weighting for FedEx Express and oversize conditions for FedEx
  * Express and FedEx Ground.
  *
  * @param length
  *   Indicate the length of the package. No implied decimal places. Maximum value: 999 <br> Example: 20
  * @param width
  *   Indicate the width of the package. No implied decimal places. Maximum value: 999 <br> Example: 10
  * @param height
  *   Indicate the height of the package. No implied decimal places. Maximum value: 999 <br> Example: 10
  * @param units
  *   Indicate the Unit of measure for the provided dimensions.<br>Valid Values are:<li>IN - inches</li><li>CM - centimeters</li>Note: Any
  *   value other than CM including blank/null will default to IN.
  */
case class Dimensions(
    length: Option[Int] = None,
    width: Option[Int] = None,
    height: Option[Int] = None,
    units: Option[Dimensions.Units] = None
)

object Dimensions {
  enum Units {
    case CM
    case IN
    case UNKNOWN_DEFAULT
  }
  object Units {
    given Encoder[Units] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Units] = Decoder.decodeString.map(s => scala.util.Try(Units.valueOf(s)).getOrElse(Units.UNKNOWN_DEFAULT))
  }
  given Encoder[Dimensions] = new Encoder.AsObject[Dimensions] {
    final def encodeObject(o: Dimensions): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "length" -> o.length.asJson,
          "width"  -> o.width.asJson,
          "height" -> o.height.asJson,
          "units"  -> o.units.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Dimensions] = (c: HCursor) => {
    for {
      length <- c.downField("length").as[Option[Int]]
      width  <- c.downField("width").as[Option[Int]]
      height <- c.downField("height").as[Option[Int]]
      units  <- c.downField("units").as[Option[Units]]
    } yield Dimensions(length, width, height, units)
  }
}
