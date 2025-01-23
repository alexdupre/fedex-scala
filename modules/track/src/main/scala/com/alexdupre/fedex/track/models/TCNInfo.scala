package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** TCNInfo Model
  *
  * @param value
  *   Field which holds the Transportation Control Number value.<br> Example: N552428361Y555XXX
  * @param carrierCode
  *   Field which holds information about carrier code of the shipment.<br> Example: FDXE
  * @param shipDateBegin
  *   ShipDateBegin and ShipDateEnd are recommended to narrow the search, reduce lookup time, and avoid duplicates when searching for a
  *   specific tracking number within a specific date range. <br>Format: YYYY-MM-DD <br> Example: 2020-03-29
  * @param shipDateEnd
  *   ShipDateBegin and ShipDateEnd are recommended to narrow the search, reduce lookup time, and avoid duplicates when searching for a
  *   specific tracking number within a specific date range. <br>Format: YYYY-MM-DD <br> Example: 2020-04-01
  */
case class TCNInfo(
    value: String,
    carrierCode: Option[String] = None,
    shipDateBegin: Option[String] = None,
    shipDateEnd: Option[String] = None
)

object TCNInfo {

  given Encoder[TCNInfo] = new Encoder.AsObject[TCNInfo] {
    final def encodeObject(o: TCNInfo): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "value"         -> o.value.asJson,
          "carrierCode"   -> o.carrierCode.asJson,
          "shipDateBegin" -> o.shipDateBegin.asJson,
          "shipDateEnd"   -> o.shipDateEnd.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TCNInfo] = (c: HCursor) => {
    for {
      value         <- c.downField("value").as[String]
      carrierCode   <- c.downField("carrierCode").as[Option[String]]
      shipDateBegin <- c.downField("shipDateBegin").as[Option[String]]
      shipDateEnd   <- c.downField("shipDateEnd").as[Option[String]]
    } yield TCNInfo(value, carrierCode, shipDateBegin, shipDateEnd)
  }
}
