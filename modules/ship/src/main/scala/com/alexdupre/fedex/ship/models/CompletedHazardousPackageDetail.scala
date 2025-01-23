package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Complete package-level hazardous commodity information for a single package.
  *
  * @param regulation
  *   Specifies the hazardous package regulation type.<br>Example: IATA
  * @param accessibility
  *   Specifies the hazardous package accessibility.<br>Example: ACCESSIBLE
  * @param labelType
  *   Specifies the label type of hazardous package.<br>Example: II_YELLOW
  * @param containers
  *   Indicates one or more approved containers used to pack dangerous goods commodities. This does not describe any individual inner
  *   receptacles that may be within this container.
  * @param cargoAircraftOnly
  *   When TRUE-indicates that the package can be transported only on a cargo aircraft.<br>Example: true
  * @param referenceId
  *   A unique reference id that matches the package to a package configuration. This is populated if the client provided a package
  *   configuration for several packages that have the exact same dangerous goods content.<br>Example: 123456
  * @param radioactiveTransportIndex
  *   Specifies the maximum radiation level from the package (measured in microSieverts per hour at a distance of one meter from the
  *   external surface of the package, divided by 10).<br>Example: 2.45
  */
case class CompletedHazardousPackageDetail(
    regulation: Option[String] = None,
    accessibility: Option[String] = None,
    labelType: Option[String] = None,
    containers: Option[Seq[ValidatedHazardousContainer]] = None,
    cargoAircraftOnly: Option[Boolean] = None,
    referenceId: Option[String] = None,
    radioactiveTransportIndex: Option[Double] = None
)

object CompletedHazardousPackageDetail {

  given Encoder[CompletedHazardousPackageDetail] = new Encoder.AsObject[CompletedHazardousPackageDetail] {
    final def encodeObject(o: CompletedHazardousPackageDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "regulation"                -> o.regulation.asJson,
          "accessibility"             -> o.accessibility.asJson,
          "labelType"                 -> o.labelType.asJson,
          "containers"                -> o.containers.asJson,
          "cargoAircraftOnly"         -> o.cargoAircraftOnly.asJson,
          "referenceId"               -> o.referenceId.asJson,
          "radioactiveTransportIndex" -> o.radioactiveTransportIndex.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CompletedHazardousPackageDetail] = (c: HCursor) => {
    for {
      regulation                <- c.downField("regulation").as[Option[String]]
      accessibility             <- c.downField("accessibility").as[Option[String]]
      labelType                 <- c.downField("labelType").as[Option[String]]
      containers                <- c.downField("containers").as[Option[Seq[ValidatedHazardousContainer]]]
      cargoAircraftOnly         <- c.downField("cargoAircraftOnly").as[Option[Boolean]]
      referenceId               <- c.downField("referenceId").as[Option[String]]
      radioactiveTransportIndex <- c.downField("radioactiveTransportIndex").as[Option[Double]]
    } yield CompletedHazardousPackageDetail(
      regulation,
      accessibility,
      labelType,
      containers,
      cargoAircraftOnly,
      referenceId,
      radioactiveTransportIndex
    )
  }
}
