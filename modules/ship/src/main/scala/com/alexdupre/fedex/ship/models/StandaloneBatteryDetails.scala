package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** StandaloneBatteryDetails Model
  *
  * @param batteryMaterialType
  *   Describes the material composition of the battery or cell.
  */
case class StandaloneBatteryDetails(
    batteryMaterialType: Option[StandaloneBatteryDetails.BatteryMaterialType] = None
)

object StandaloneBatteryDetails {
  enum BatteryMaterialType {
    case LITHIUM_METAL
    case LITHIUM_ION
  }
  object BatteryMaterialType {
    given Encoder[BatteryMaterialType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[BatteryMaterialType] = Decoder.decodeString.emapTry(s => scala.util.Try(BatteryMaterialType.valueOf(s)))
  }
  given Encoder[StandaloneBatteryDetails] = new Encoder.AsObject[StandaloneBatteryDetails] {
    final def encodeObject(o: StandaloneBatteryDetails): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "batteryMaterialType" -> o.batteryMaterialType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[StandaloneBatteryDetails] = (c: HCursor) => {
    for {
      batteryMaterialType <- c.downField("batteryMaterialType").as[Option[BatteryMaterialType]]
    } yield StandaloneBatteryDetails(batteryMaterialType)
  }
}
