package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** BatteryDetail Model
  *
  * @param batteryPackingType
  *   Describe the packing arrangement of the battery or cell with respect to other items within the same package.
  * @param batteryRegulatoryType
  *   This is a regulation specific classification for the battery or the cell.
  * @param batteryMaterialType
  *   Indicate the material composition of the battery or cell.
  */
case class BatteryDetail(
    batteryPackingType: Option[BatteryDetail.BatteryPackingType] = None,
    batteryRegulatoryType: Option[BatteryDetail.BatteryRegulatoryType] = None,
    batteryMaterialType: Option[BatteryDetail.BatteryMaterialType] = None
)

object BatteryDetail {
  enum BatteryPackingType {
    case CONTAINED_IN_EQUIPMENT
    case PACKED_WITH_EQUIPMENT
    case UNKNOWN_DEFAULT
  }
  object BatteryPackingType {
    given Encoder[BatteryPackingType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[BatteryPackingType] =
      Decoder.decodeString.map(s => scala.util.Try(BatteryPackingType.valueOf(s)).getOrElse(BatteryPackingType.UNKNOWN_DEFAULT))
  }

  enum BatteryRegulatoryType {
    case IATA_SECTION_II
    case UNKNOWN_DEFAULT
  }
  object BatteryRegulatoryType {
    given Encoder[BatteryRegulatoryType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[BatteryRegulatoryType] =
      Decoder.decodeString.map(s => scala.util.Try(BatteryRegulatoryType.valueOf(s)).getOrElse(BatteryRegulatoryType.UNKNOWN_DEFAULT))
  }

  enum BatteryMaterialType {
    case LITHIUM_METAL
    case LITHIUM_ION
    case UNKNOWN_DEFAULT
  }
  object BatteryMaterialType {
    given Encoder[BatteryMaterialType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[BatteryMaterialType] =
      Decoder.decodeString.map(s => scala.util.Try(BatteryMaterialType.valueOf(s)).getOrElse(BatteryMaterialType.UNKNOWN_DEFAULT))
  }
  given Encoder[BatteryDetail] = new Encoder.AsObject[BatteryDetail] {
    final def encodeObject(o: BatteryDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "batteryPackingType"    -> o.batteryPackingType.asJson,
          "batteryRegulatoryType" -> o.batteryRegulatoryType.asJson,
          "batteryMaterialType"   -> o.batteryMaterialType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[BatteryDetail] = (c: HCursor) => {
    for {
      batteryPackingType    <- c.downField("batteryPackingType").as[Option[BatteryPackingType]]
      batteryRegulatoryType <- c.downField("batteryRegulatoryType").as[Option[BatteryRegulatoryType]]
      batteryMaterialType   <- c.downField("batteryMaterialType").as[Option[BatteryMaterialType]]
    } yield BatteryDetail(batteryPackingType, batteryRegulatoryType, batteryMaterialType)
  }
}
