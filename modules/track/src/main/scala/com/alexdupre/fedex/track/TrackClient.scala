package com.alexdupre.fedex.track

import sttp.client4.Backend
import sttp.monad.MonadError
import sttp.monad.syntax.*

/** Track API 1.0.0
  *
  * @param baseUrl
  *   the server base url
  */
class TrackClient[F[_]](baseUrl: String = "https://apis-sandbox.fedex.com")(using backend: Backend[F]) {

  private val api = new TrackAPI(baseUrl)

  private given monad: MonadError[F] = backend.monad

  /** Track Multiple Piece Shipment This endpoint returns tracking information for multiplee piece shipments, Group MPS, or an outbounddd
    * shipment which is linked to a return shipment.<br><i>Note: FedEx APIs do not support Cross-Origin Resource Sharing (CORS)
    * mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param body
    *   The request elements for Tracking by associated shipment.
    * @param xCustomerTransactionId
    *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
    *   match the request to the reply.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def trackMultiplePieceShipment(
      authorization: String,
      body: models.FullSchemaMultiplePieceShipment,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.TrkcResponseVOAssociated] = {
    api
      .trackMultiplePieceShipment(authorization, body, xCustomerTransactionId, xLocale)
      .send(backend)
      .flatMap(_.body.fold(monad.error, monad.unit))
  }

  /** Send Notification This endpoint helps you setup up, customize the tracking event notifications to be received for a
    * shipment.<br><i>Note: FedEx APIs do not support Cross-Origin Resource Sharing (CORS) mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param body
    *   The request to receive a tracking notification.
    * @param xCustomerTransactionId
    *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
    *   match the request to the reply.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def sendNotification(
      authorization: String,
      body: models.FullSchemaNotification,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.TrkcResponseVONotifications] = {
    api
      .sendNotification(authorization, body, xCustomerTransactionId, xLocale)
      .send(backend)
      .flatMap(_.body.fold(monad.error, monad.unit))
  }

  /** Track by References This endpoint returns tracking information based on alternate references other than Tracking Number such as
    * Customer reference numbers, etc.<br><i>Note: FedEx APIs do not support Cross-Origin Resource Sharing (CORS) mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param body
    *   Specifies the request elements for Track by alternate reference.<br> For a valid request there are two combinations:<br> 1.) A
    *   referenceValue and accountNumber is required OR <br>2.) referenceType & carrierCode, shipdateBegin and shipDateEnd,
    *   destinationPostalCode and destinationCountryCode are required.
    * @param xCustomerTransactionId
    *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
    *   match the request to the reply.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def trackByReferences(
      authorization: String,
      body: models.FullSchemaTrackingReferences,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.TrkcResponseVOReferenceNumber] = {
    api
      .trackByReferences(authorization, body, xCustomerTransactionId, xLocale)
      .send(backend)
      .flatMap(_.body.fold(monad.error, monad.unit))
  }

  /** Track by Tracking Control Number Use this endpoint to return tracking information based on a Tracking Control Number.<br><i>Note:
    * FedEx APIs do not support Cross-Origin Resource Sharing (CORS) mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param body
    *   The request elements for Tracking by TCN request type.
    * @param xCustomerTransactionId
    *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
    *   match the request to the reply.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def trackByTrackingControlNumber(
      authorization: String,
      body: models.FullSchemaTCN,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.TrkcResponseVOTCN] = {
    api
      .trackByTrackingControlNumber(authorization, body, xCustomerTransactionId, xLocale)
      .send(backend)
      .flatMap(_.body.fold(monad.error, monad.unit))
  }

  /** Track Document This endpoint helps you to request a letter that includes the recipient's signature as a proof of delivery.<br><i>Note:
    * FedEx APIs do not support Cross-Origin Resource Sharing (CORS) mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param xCustomerTransactionId
    *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
    *   match the request to the reply.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def trackDocument(
      authorization: String,
      body: models.FullSchemaSPOD,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.TrkcResponseVOSPOD] = {
    api
      .trackDocument(authorization, body, xCustomerTransactionId, xLocale)
      .send(backend)
      .flatMap(_.body.fold(monad.error, monad.unit))
  }

  /** Track by Tracking Number This endpoint provides customers package tracking information based on a tracking number for various shipping
    * services.<br><i>Note: FedEx APIs do not support Cross-Origin Resource Sharing (CORS) mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param body
    *   The request elements for Tracking by Tracking Number.
    * @param xCustomerTransactionId
    *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
    *   match the request to the reply.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def trackByTrackingNumber(
      authorization: String,
      body: models.FullSchemaTrackingNumbers,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.TrkcResponseVOTrackingNumber] = {
    api
      .trackByTrackingNumber(authorization, body, xCustomerTransactionId, xLocale)
      .send(backend)
      .flatMap(_.body.fold(monad.error, monad.unit))
  }
}
