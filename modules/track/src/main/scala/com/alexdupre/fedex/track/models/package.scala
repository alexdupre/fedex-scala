package com.alexdupre.fedex.track

package object models {

  type BaseProcessOutputVOAssociated      = TrackingMPSResponse
  type BaseProcessOutputVONotification    = SendNotificationOutputVO
  type BaseProcessOutputVOReferenceNumber = TrackingReferencesResponse
  type BaseProcessOutputVOSPOD            = SPODResponseVO
  type BaseProcessOutputVOTCN             = TrackingNumbersResponseTCN
  type BaseProcessOutputVOTrackingNumber  = TrackingNumbersResponse
  type MasterTrackingInfo                 = TrackingInfo
  type NotificationEventTypes             = Seq[String]
  type OriginLocation                     = LocationDetailOrigin
  type PagingDetails                      = PagingDetails2
  type TrackingEventNotificationDetail    = TrackingEventNotificationDetail2
}
