package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** FedEx scanning event information for a package.<br><a onclick='loadDocReference("trackservicescancodes")'>Click here to see Track
  * Service Scan Codes.</a>
  *
  * @param date
  *   Date and time of the scan event.<br> Example: '2018-02-02T12:01:00-07:00'
  * @param derivedStatus
  *   Field which holds status description of the track information for the scan event.<br> Example: 'Picked Up'
  * @param scanLocation
  *   Location Details for the FedEx facility where the scan event occurred.
  * @param locationId
  *   Location Identification for facilities identified by an alpha numeric location code. Passing Location Id of the Hold at Location (HAL)
  *   address is strongly recommended to ensure packages are delivered to the correct address.<br> Example: SEA
  * @param locationType
  *   This field holds Location Type. If Location Type not available we will get empty value
  * @param exceptionDescription
  *   Field which holds the text description for the exception if the event was an exception .<br>Example: Package available for clearance
  * @param eventDescription
  *   Field which holds the text description of the scan event.<br> Example: 'Picked Up'
  * @param eventType
  *   Field which holds the code identifying the type of scan event.<br> Example: 'PU'
  * @param derivedStatusCode
  *   Field which holds status code of the track information for the scan event.<br> Example: 'PU'
  * @param exceptionCode
  *   Field which holds the code identifier for the exception if the event was an exception. <br> Example: A25
  */
case class ScanEvent(
    date: Option[String] = None,
    derivedStatus: Option[String] = None,
    scanLocation: Option[AddressVO1] = None,
    locationId: Option[String] = None,
    locationType: Option[ScanEvent.LocationType] = None,
    exceptionDescription: Option[String] = None,
    eventDescription: Option[String] = None,
    eventType: Option[String] = None,
    derivedStatusCode: Option[String] = None,
    exceptionCode: Option[String] = None,
    delayDetail: Option[DelayDetail] = None
)

object ScanEvent {
  enum LocationType {
    case AIRPORT
    case CUSTOMS_BROKER
    case CUSTOMER
    case DELIVERY_LOCATION
    case DESTINATION_AIRPORT
    case DROP_BOX
    case DESTINATION_FEDEX_FACILITY
    case ENROUTE
    case FEDEX_FACILITY
    case INTERLINE_CARRIER
    case FEDEX_OFFICE_LOCATION
    case NON_FEDEX_FACILITY
    case ORIGIN_AIRPORT
    case ORIGIN_FEDEX_FACILITY
    case PORT_OF_ENTRY
    case PICKUP_LOCATION
    case PLANE
    case SORT_FACILITY
    case SHIP_AND_GET_LOCATION
    case TURNPOINT
    case VEHICLE
  }
  object LocationType {
    given Encoder[LocationType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[LocationType] = Decoder.decodeString.emapTry(s => scala.util.Try(LocationType.valueOf(s)))
  }
  given Encoder[ScanEvent] = new Encoder.AsObject[ScanEvent] {
    final def encodeObject(o: ScanEvent): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "date"                 -> o.date.asJson,
          "derivedStatus"        -> o.derivedStatus.asJson,
          "scanLocation"         -> o.scanLocation.asJson,
          "locationId"           -> o.locationId.asJson,
          "locationType"         -> o.locationType.asJson,
          "exceptionDescription" -> o.exceptionDescription.asJson,
          "eventDescription"     -> o.eventDescription.asJson,
          "eventType"            -> o.eventType.asJson,
          "derivedStatusCode"    -> o.derivedStatusCode.asJson,
          "exceptionCode"        -> o.exceptionCode.asJson,
          "delayDetail"          -> o.delayDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ScanEvent] = (c: HCursor) => {
    for {
      date                 <- c.downField("date").as[Option[String]]
      derivedStatus        <- c.downField("derivedStatus").as[Option[String]]
      scanLocation         <- c.downField("scanLocation").as[Option[AddressVO1]]
      locationId           <- c.downField("locationId").as[Option[String]]
      locationType         <- c.downField("locationType").as[Option[LocationType]]
      exceptionDescription <- c.downField("exceptionDescription").as[Option[String]]
      eventDescription     <- c.downField("eventDescription").as[Option[String]]
      eventType            <- c.downField("eventType").as[Option[String]]
      derivedStatusCode    <- c.downField("derivedStatusCode").as[Option[String]]
      exceptionCode        <- c.downField("exceptionCode").as[Option[String]]
      delayDetail          <- c.downField("delayDetail").as[Option[DelayDetail]]
    } yield ScanEvent(
      date,
      derivedStatus,
      scanLocation,
      locationId,
      locationType,
      exceptionDescription,
      eventDescription,
      eventType,
      derivedStatusCode,
      exceptionCode,
      delayDetail
    )
  }
}
