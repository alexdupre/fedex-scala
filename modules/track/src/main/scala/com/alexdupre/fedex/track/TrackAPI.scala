package com.alexdupre.fedex.track

import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import sttp.client4.circe.*
import sttp.client4.*

/** Track API 1.0.0
  *
  * @param baseUrl
  *   the server base url
  */
class TrackAPI(baseUrl: String = "https://apis-sandbox.fedex.com") {
  private def cleanAndStringify(in: Map[String, Any]): Map[String, String] = {
    in.flatMap {
      case (_, None)          => None
      case (key, Some(value)) => Some(key -> value.toString)
      case (key, value)       => Some(key -> value.toString)
    }
  }

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
  ): Request[Either[TrackAPIException, models.TrkcResponseVOAssociated]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .post(uri"$baseUrl/track/v1/associatedshipments")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.TrkcResponseVOAssociated].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO])
            .fold(e => throw e, o => TrackAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401])
            .fold(e => throw e, o => TrackAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403])
            .fold(e => throw e, o => TrackAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404])
            .fold(e => throw e, o => TrackAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500])
            .fold(e => throw e, o => TrackAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503])
            .fold(e => throw e, o => TrackAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
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
  ): Request[Either[TrackAPIException, models.TrkcResponseVONotifications]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .post(uri"$baseUrl/track/v1/notifications")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.TrkcResponseVONotifications].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO])
            .fold(e => throw e, o => TrackAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401])
            .fold(e => throw e, o => TrackAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403])
            .fold(e => throw e, o => TrackAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404])
            .fold(e => throw e, o => TrackAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500])
            .fold(e => throw e, o => TrackAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503])
            .fold(e => throw e, o => TrackAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
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
  ): Request[Either[TrackAPIException, models.TrkcResponseVOReferenceNumber]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .post(uri"$baseUrl/track/v1/referencenumbers")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.TrkcResponseVOReferenceNumber].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO])
            .fold(e => throw e, o => TrackAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401])
            .fold(e => throw e, o => TrackAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403])
            .fold(e => throw e, o => TrackAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404])
            .fold(e => throw e, o => TrackAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500])
            .fold(e => throw e, o => TrackAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503])
            .fold(e => throw e, o => TrackAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
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
  ): Request[Either[TrackAPIException, models.TrkcResponseVOTCN]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .post(uri"$baseUrl/track/v1/tcn")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.TrkcResponseVOTCN].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO])
            .fold(e => throw e, o => TrackAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401])
            .fold(e => throw e, o => TrackAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403])
            .fold(e => throw e, o => TrackAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404])
            .fold(e => throw e, o => TrackAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500])
            .fold(e => throw e, o => TrackAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503])
            .fold(e => throw e, o => TrackAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
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
  ): Request[Either[TrackAPIException, models.TrkcResponseVOSPOD]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .post(uri"$baseUrl/track/v1/trackingdocuments")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.TrkcResponseVOSPOD].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO])
            .fold(e => throw e, o => TrackAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401])
            .fold(e => throw e, o => TrackAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403])
            .fold(e => throw e, o => TrackAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404])
            .fold(e => throw e, o => TrackAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500])
            .fold(e => throw e, o => TrackAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503])
            .fold(e => throw e, o => TrackAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
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
  ): Request[Either[TrackAPIException, models.TrkcResponseVOTrackingNumber]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .post(uri"$baseUrl/track/v1/trackingnumbers")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.TrkcResponseVOTrackingNumber].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO])
            .fold(e => throw e, o => TrackAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401])
            .fold(e => throw e, o => TrackAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403])
            .fold(e => throw e, o => TrackAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404])
            .fold(e => throw e, o => TrackAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500])
            .fold(e => throw e, o => TrackAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503])
            .fold(e => throw e, o => TrackAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
  }

}

object TrackAPI {
  object Servers {
    val SandboxServer    = "https://apis-sandbox.fedex.com"
    val ProductionServer = "https://apis.fedex.com"
  }
}
