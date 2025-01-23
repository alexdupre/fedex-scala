package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Descriptions for a service.
  *
  * @param serviceType
  *   Indicate the FedEx serviceType used for this shipment. The results will be filtered by the serviceType value indicated.<br>Example:
  *   STANDARD_OVERNIGHT<br><a onclick='loadDocReference("servicetypes")'>click here to see Service Types</a>
  * @param code
  *   Specifies code of the Service.<br>example: 80
  * @param names
  *   Branded, translated, and/or localized names for this service.
  * @param operatingOrgCodes
  *   FOR FEDEX INTERNAL USE ONLY. The operating org code in a service.<br>Example: ["FXE", "FXE"]
  * @param astraDescription
  *   Specifies astra Description.<br>Example: 2 DAY FRT
  * @param description
  *   specifies the description.<br>Example:description
  * @param serviceId
  *   FOR FEDEX INTERNAL USE ONLY, Designates the service ID.<br>Example: EP1000000027
  * @param serviceCategory
  *   FOR FEDEX INTERNAL USE ONLY. This is tied to the Product EFS interface definition which will currently contain the values of
  *   parcel.<br>Example: EXPRESS_PARCEL
  */
case class ServiceDescription(
    serviceType: Option[String] = None,
    code: Option[String] = None,
    names: Option[Seq[ProductName]] = None,
    operatingOrgCodes: Option[Seq[String]] = None,
    astraDescription: Option[String] = None,
    description: Option[String] = None,
    serviceId: Option[String] = None,
    serviceCategory: Option[String] = None
)

object ServiceDescription {

  given Encoder[ServiceDescription] = new Encoder.AsObject[ServiceDescription] {
    final def encodeObject(o: ServiceDescription): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "serviceType"       -> o.serviceType.asJson,
          "code"              -> o.code.asJson,
          "names"             -> o.names.asJson,
          "operatingOrgCodes" -> o.operatingOrgCodes.asJson,
          "astraDescription"  -> o.astraDescription.asJson,
          "description"       -> o.description.asJson,
          "serviceId"         -> o.serviceId.asJson,
          "serviceCategory"   -> o.serviceCategory.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ServiceDescription] = (c: HCursor) => {
    for {
      serviceType       <- c.downField("serviceType").as[Option[String]]
      code              <- c.downField("code").as[Option[String]]
      names             <- c.downField("names").as[Option[Seq[ProductName]]]
      operatingOrgCodes <- c.downField("operatingOrgCodes").as[Option[Seq[String]]]
      astraDescription  <- c.downField("astraDescription").as[Option[String]]
      description       <- c.downField("description").as[Option[String]]
      serviceId         <- c.downField("serviceId").as[Option[String]]
      serviceCategory   <- c.downField("serviceCategory").as[Option[String]]
    } yield ServiceDescription(serviceType, code, names, operatingOrgCodes, astraDescription, description, serviceId, serviceCategory)
  }
}
