package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Delivery related information for the tracked package. Provides delivery details as actual delivery address once the package is
  * delivered, the number of delivery attempts made etc.
  *
  * @param receivedByName
  *   Field which holds the name of the person who received the package, if applicable.<br> Example: Receiver
  * @param destinationServiceArea
  *   Field which holds the destination service area code.<br> Example: EDDUNAVAILABLE
  * @param destinationServiceAreaDescription
  *   Field which holds the description corresponding to the destination service area.<br> Example: Appointment Required
  * @param locationDescription
  *   Field which holds the FedEx location description for the package destination.<br> Example: Receptionist/Front Desk
  * @param deliveryToday
  *   This element indicates whether the package will be delivered today. The value 'True', indicates that today is package delivery.<br>
  *   Example: true
  * @param locationType
  *   Field which holds the FedEx location type code for the package destination. If Location Type not available we will get empty value.
  * @param signedByName
  *   Field which holds the name of the person who signed for the package, if applicable.<br> Example: Reciever
  * @param officeOrderDeliveryMethod
  *   Field which identifies the method of office order delivery. 'Pickup' - the recipient will be picking up the office order from the
  *   FedEx Office Center. 'Shipment' - the office order will be delivered to the recipient as a FedEx shipment using the FedEx Service Type
  *   requested. 'Courier' - the office order will be delivered to the recipient by local courier. <br> Example: Courier
  * @param deliveryAttempts
  *   Field which holds the number of delivery attempts made to deliver the package.<br> Example: 0
  * @param deliveryOptionEligibilityDetails
  *   Specifies eligibility type for the different delivery option.
  */
case class DeliveryDetails(
    receivedByName: Option[String] = None,
    destinationServiceArea: Option[String] = None,
    destinationServiceAreaDescription: Option[String] = None,
    locationDescription: Option[String] = None,
    actualDeliveryAddress: Option[AddressVO1] = None,
    deliveryToday: Option[Boolean] = None,
    locationType: Option[DeliveryDetails.LocationType] = None,
    signedByName: Option[String] = None,
    officeOrderDeliveryMethod: Option[String] = None,
    deliveryAttempts: Option[String] = None,
    deliveryOptionEligibilityDetails: Option[Seq[DeliveryOptionElgibilityDetails]] = None
)

object DeliveryDetails {
  enum LocationType {
    case RECEPTIONIST_OR_FRONT_DESK
    case SHIPPING_RECEIVING
    case MAILROOM
    case RESIDENCE
    case GUARD_OR_SECURITY_STATION
    case FEDEX_LOCATION
    case IN_BOND_OR_CAGE
    case PHARMACY
    case GATE_HOUSE
    case MANAGER_OFFICE
    case MAIN_OFFICE
    case LEASING_OFFICE
    case RENTAL_OFFICE
    case APARTMENT_OFFICE
    case OTHER
  }
  object LocationType {
    given Encoder[LocationType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[LocationType] = Decoder.decodeString.emapTry(s => scala.util.Try(LocationType.valueOf(s)))
  }
  given Encoder[DeliveryDetails] = new Encoder.AsObject[DeliveryDetails] {
    final def encodeObject(o: DeliveryDetails): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "receivedByName"                    -> o.receivedByName.asJson,
          "destinationServiceArea"            -> o.destinationServiceArea.asJson,
          "destinationServiceAreaDescription" -> o.destinationServiceAreaDescription.asJson,
          "locationDescription"               -> o.locationDescription.asJson,
          "actualDeliveryAddress"             -> o.actualDeliveryAddress.asJson,
          "deliveryToday"                     -> o.deliveryToday.asJson,
          "locationType"                      -> o.locationType.asJson,
          "signedByName"                      -> o.signedByName.asJson,
          "officeOrderDeliveryMethod"         -> o.officeOrderDeliveryMethod.asJson,
          "deliveryAttempts"                  -> o.deliveryAttempts.asJson,
          "deliveryOptionEligibilityDetails"  -> o.deliveryOptionEligibilityDetails.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DeliveryDetails] = (c: HCursor) => {
    for {
      receivedByName                    <- c.downField("receivedByName").as[Option[String]]
      destinationServiceArea            <- c.downField("destinationServiceArea").as[Option[String]]
      destinationServiceAreaDescription <- c.downField("destinationServiceAreaDescription").as[Option[String]]
      locationDescription               <- c.downField("locationDescription").as[Option[String]]
      actualDeliveryAddress             <- c.downField("actualDeliveryAddress").as[Option[AddressVO1]]
      deliveryToday                     <- c.downField("deliveryToday").as[Option[Boolean]]
      locationType                      <- c.downField("locationType").as[Option[LocationType]]
      signedByName                      <- c.downField("signedByName").as[Option[String]]
      officeOrderDeliveryMethod         <- c.downField("officeOrderDeliveryMethod").as[Option[String]]
      deliveryAttempts                  <- c.downField("deliveryAttempts").as[Option[String]]
      deliveryOptionEligibilityDetails  <- c.downField("deliveryOptionEligibilityDetails").as[Option[Seq[DeliveryOptionElgibilityDetails]]]
    } yield DeliveryDetails(
      receivedByName,
      destinationServiceArea,
      destinationServiceAreaDescription,
      locationDescription,
      actualDeliveryAddress,
      deliveryToday,
      locationType,
      signedByName,
      officeOrderDeliveryMethod,
      deliveryAttempts,
      deliveryOptionEligibilityDetails
    )
  }
}
