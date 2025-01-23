package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are one or more package-attribute descriptions, each of which describes an individual package, a group of identical packages, or
  * (for the total-piece-total-weight case) common characteristics of all packages in the shipment.<ul><li>At least one instance containing
  * the weight for at least one package is required for EXPRESS and GROUND shipments.</li><li>Single piece requests will have one
  * RequestedPackageLineItem.</li><li>Multiple piece requests will have multiple RequestedPackageLineItems.</li><li>Maximum occurrences is
  * 30.</li></ul>
  *
  * @param sequenceNumber
  *   Optional. Used only with individual packages as a unique identifier of each requested package. Will be adjusted at the shipment level
  *   as pieces are added.<br>Example: 1
  * @param subPackagingType
  *   Indicate the subPackagingType, if you are using your own packaging for the shipment. Use it for all shipments inbound to Canada (CA)
  *   and inbound shipments to the U.S. and Puerto Rico (PR) from Canada and Mexico (MX).subPackagingType is mandatory for shipments to
  *   Canada.<br>Example: TUBE, CARTON, CONTAINER. etc.<br>Note: If the value is TUBE, a non-machinable surcharge will be applicable for
  *   SmartPost shipments.<br><a onclick='loadDocReference("subpackagetypes")'>click here to see Sub-Packaging Types</a><br>For more
  *   information on physical packaging or packaging regulatory requirements, contact your FedEx representative.
  * @param customerReferences
  *   This object lists the customer references provided with the package.
  * @param declaredValue
  *   This is the Declared Value of any shipment which represents FedEx maximum liability associated with a shipment. This is including, but
  *   not limited to any loss, damage, delay, misdelivery, any failure to provide information, or misdelivery of information related to the
  *   Shipment.
  * @param dimensions
  *   Indicate the dimensions of the package.<br> Following conditions will apply: <ul><li>Dimensions are optional but when added, then all
  *   three dimensions must be indicated.</li><li>Dimensions are required when using a Express freight service.</li></ul>Note: The
  *   maximum/minimum dimension values varies based on the services and the packaging types. Refer <a
  *   href='https://www.fedex.com/en-us/service-guide.html' target='_blank'>FedEx Service Guide</a> for service details related to DIM
  *   Weighting for FedEx Express and oversize conditions for FedEx Express and FedEx Ground.
  * @param groupPackageCount
  *   Indicate the grouped package count. These are number of identical package(s) each with one or more commodities. <br> Example: 2
  * @param itemDescriptionForClearance
  *   Package description used for clearance. The value is required for intra-UAE. and is optional for intra-EU.<br>Example:description
  * @param contentRecord
  *   Use this object to specify package content details.
  * @param itemDescription
  *   This the item description for the package.<br>Note: Item description is required for Email Label return shipments and ground Create
  *   tag.<br>Example: Item description<br> Maximum limit is 50 characters
  */
case class RequestedPackageLineItem(
    weight: Weight,
    sequenceNumber: Option[Int] = None,
    subPackagingType: Option[String] = None,
    customerReferences: Option[Seq[CustomerReference1]] = None,
    declaredValue: Option[Money] = None,
    dimensions: Option[Dimensions] = None,
    groupPackageCount: Option[Int] = None,
    itemDescriptionForClearance: Option[String] = None,
    contentRecord: Option[Seq[ContentRecord]] = None,
    itemDescription: Option[String] = None,
    variableHandlingChargeDetail: Option[VariableHandlingChargeDetail] = None,
    packageSpecialServices: Option[PackageSpecialServicesRequested] = None
)

object RequestedPackageLineItem {

  given Encoder[RequestedPackageLineItem] = new Encoder.AsObject[RequestedPackageLineItem] {
    final def encodeObject(o: RequestedPackageLineItem): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "weight"                       -> o.weight.asJson,
          "sequenceNumber"               -> o.sequenceNumber.asJson,
          "subPackagingType"             -> o.subPackagingType.asJson,
          "customerReferences"           -> o.customerReferences.asJson,
          "declaredValue"                -> o.declaredValue.asJson,
          "dimensions"                   -> o.dimensions.asJson,
          "groupPackageCount"            -> o.groupPackageCount.asJson,
          "itemDescriptionForClearance"  -> o.itemDescriptionForClearance.asJson,
          "contentRecord"                -> o.contentRecord.asJson,
          "itemDescription"              -> o.itemDescription.asJson,
          "variableHandlingChargeDetail" -> o.variableHandlingChargeDetail.asJson,
          "packageSpecialServices"       -> o.packageSpecialServices.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RequestedPackageLineItem] = (c: HCursor) => {
    for {
      weight                       <- c.downField("weight").as[Weight]
      sequenceNumber               <- c.downField("sequenceNumber").as[Option[Int]]
      subPackagingType             <- c.downField("subPackagingType").as[Option[String]]
      customerReferences           <- c.downField("customerReferences").as[Option[Seq[CustomerReference1]]]
      declaredValue                <- c.downField("declaredValue").as[Option[Money]]
      dimensions                   <- c.downField("dimensions").as[Option[Dimensions]]
      groupPackageCount            <- c.downField("groupPackageCount").as[Option[Int]]
      itemDescriptionForClearance  <- c.downField("itemDescriptionForClearance").as[Option[String]]
      contentRecord                <- c.downField("contentRecord").as[Option[Seq[ContentRecord]]]
      itemDescription              <- c.downField("itemDescription").as[Option[String]]
      variableHandlingChargeDetail <- c.downField("variableHandlingChargeDetail").as[Option[VariableHandlingChargeDetail]]
      packageSpecialServices       <- c.downField("packageSpecialServices").as[Option[PackageSpecialServicesRequested]]
    } yield RequestedPackageLineItem(
      weight,
      sequenceNumber,
      subPackagingType,
      customerReferences,
      declaredValue,
      dimensions,
      groupPackageCount,
      itemDescriptionForClearance,
      contentRecord,
      itemDescription,
      variableHandlingChargeDetail,
      packageSpecialServices
    )
  }
}
