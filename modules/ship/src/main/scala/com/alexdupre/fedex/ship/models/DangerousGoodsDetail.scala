package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Provide dangerous goods shipment details.
  *
  * @param cargoAircraftOnly
  *   A Boolean value that, when True, specifies the mode of shipment transportation should be Cargo Aircraft for Dangerous Goods. Its
  *   default value is set as False.<br>Note: An identifier DGD-CAO is added in AWB for cargo aircraft shipments.
  * @param regulation
  *   It is a HazardousCommodityRegulationType(The regulation under which the DG data has been validated).
  * @param accessibility
  *   Specify Dangerous Goods Accessibility Type. <ul><li>Inaccessible &ndash; it does not have to be accessable on the
  *   aircraft.</li><li>Accessible &ndash; it must be fully accessible on the aircraft, and is more strictly controlled.</li></ul><i>Note:
  *   Accessibility is only required for IATA controlled DG shipments.</i>
  * @param options
  *   Indicate type of DG being reported.<br> - SMALL_QUANTITY_EXCEPTION : It is applicable for only One Piece shipment.
  */
case class DangerousGoodsDetail(
    cargoAircraftOnly: Option[Boolean] = None,
    regulation: Option[DangerousGoodsDetail.Regulation] = None,
    accessibility: Option[DangerousGoodsDetail.Accessibility] = None,
    options: Option[Seq[DangerousGoodsDetail.Options]] = None
)

object DangerousGoodsDetail {
  enum Regulation {
    case ADR
    case DOT
    case IATA
    case ORMD
    case UNKNOWN_DEFAULT
  }
  object Regulation {
    given Encoder[Regulation] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Regulation] = Decoder.decodeString.map(s => scala.util.Try(Regulation.valueOf(s)).getOrElse(Regulation.UNKNOWN_DEFAULT))
  }

  enum Accessibility {
    case ACCESSIBLE
    case INACCESSIBLE
    case UNKNOWN_DEFAULT
  }
  object Accessibility {
    given Encoder[Accessibility] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Accessibility] =
      Decoder.decodeString.map(s => scala.util.Try(Accessibility.valueOf(s)).getOrElse(Accessibility.UNKNOWN_DEFAULT))
  }

  enum Options {
    case HAZARDOUS_MATERIALS
    case BATTERY
    case ORM_D
    case REPORTABLE_QUANTITIES
    case SMALL_QUANTITY_EXCEPTION
    case LIMITED_QUANTITIES_COMMODITIES
    case UNKNOWN_DEFAULT
  }
  object Options {
    given Encoder[Options] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Options] = Decoder.decodeString.map(s => scala.util.Try(Options.valueOf(s)).getOrElse(Options.UNKNOWN_DEFAULT))
  }
  given Encoder[DangerousGoodsDetail] = new Encoder.AsObject[DangerousGoodsDetail] {
    final def encodeObject(o: DangerousGoodsDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "cargoAircraftOnly" -> o.cargoAircraftOnly.asJson,
          "regulation"        -> o.regulation.asJson,
          "accessibility"     -> o.accessibility.asJson,
          "options"           -> o.options.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DangerousGoodsDetail] = (c: HCursor) => {
    for {
      cargoAircraftOnly <- c.downField("cargoAircraftOnly").as[Option[Boolean]]
      regulation        <- c.downField("regulation").as[Option[Regulation]]
      accessibility     <- c.downField("accessibility").as[Option[Accessibility]]
      options           <- c.downField("options").as[Option[Seq[Options]]]
    } yield DangerousGoodsDetail(cargoAircraftOnly, regulation, accessibility, options)
  }
}
