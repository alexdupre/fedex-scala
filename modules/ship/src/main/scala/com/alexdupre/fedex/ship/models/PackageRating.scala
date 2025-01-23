package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This class groups together all package-level rate data for a single package (across all rate types) as part of the response to a
  * shipping request, which groups shipment-level data together and groups package-level data by package.
  *
  * @param effectiveNetDiscount
  *   This is the difference between the list and account net charge.<br>Example: 0.0
  * @param actualRateType
  *   This is the actual rate type. It identifies which entry in the following array is considered as presenting the actual rates for the
  *   package.<br>Example: PAYOR_ACCOUNT_PACKAGE
  * @param packageRateDetails
  *   Data for a package's rates, as calculated per a specific rate type.
  */
case class PackageRating(
    effectiveNetDiscount: Option[Double] = None,
    actualRateType: Option[String] = None,
    packageRateDetails: Option[Seq[PackageRateDetail]] = None
)

object PackageRating {

  given Encoder[PackageRating] = new Encoder.AsObject[PackageRating] {
    final def encodeObject(o: PackageRating): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "effectiveNetDiscount" -> o.effectiveNetDiscount.asJson,
          "actualRateType"       -> o.actualRateType.asJson,
          "packageRateDetails"   -> o.packageRateDetails.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PackageRating] = (c: HCursor) => {
    for {
      effectiveNetDiscount <- c.downField("effectiveNetDiscount").as[Option[Double]]
      actualRateType       <- c.downField("actualRateType").as[Option[String]]
      packageRateDetails   <- c.downField("packageRateDetails").as[Option[Seq[PackageRateDetail]]]
    } yield PackageRating(effectiveNetDiscount, actualRateType, packageRateDetails)
  }
}
