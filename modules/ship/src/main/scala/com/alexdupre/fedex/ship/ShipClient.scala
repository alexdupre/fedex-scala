package com.alexdupre.fedex.ship

import sttp.client4.{Backend, Request}
import sttp.monad.MonadError
import sttp.monad.syntax.*

/** Ship API 1.0.0
  *
  * @param baseUrl
  *   the server base url
  */
class ShipClient[F[_]](baseUrl: String = "https://apis-sandbox.fedex.com")(using backend: Backend[F]) {

  private val api = new ShipAPI(baseUrl)

  private given monad: MonadError[F] = backend.monad

  private def send[T](request: Request[Either[ShipException, T]]): F[T] = {
    backend.send(request).flatMap(_.body.fold(monad.error, monad.unit))
  }

  /** Create Shipment This endpoint helps you to create shipment requests thereby validating all the shipping input information and either
    * generates the labels (if the responses is synchronous) or a job ID if transaction is processed using asynchronous method.<br><i>Note:
    * FedEx APIs do not support Cross-Origin Resource Sharing (CORS) mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param body
    *   The request elements required to create a shipment.
    * @param xCustomerTransactionId
    *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
    *   match the request to the reply.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def createShipment(
      authorization: String,
      body: models.FullSchemaShip,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.SHPCResponseVOShipShipment] = {
    send(api.createShipment(authorization, body, xCustomerTransactionId, xLocale))
  }

  /** Cancel Shipment Use this endpoint to cancel FedEx Express and Ground shipments that have not already been tendered to FedEx. This
    * request will cancel all packages within the shipment.<br><i>Note: FedEx APIs do not support Cross-Origin Resource Sharing (CORS)
    * mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param body
    *   The request elements required to cancel a shipment.
    * @param xCustomerTransactionId
    *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
    *   match the request to the reply.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def cancelShipment(
      authorization: String,
      body: models.FullSchemaCancelShipment,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.SHPCResponseVOCancelShipment] = {
    send(api.cancelShipment(authorization, body, xCustomerTransactionId, xLocale))
  }

  /** Retrieve Async Ship This endpoint helps you to process confirmed shipments asynchronously (above 40 packages) and produce results
    * based on job id.<br><i>Note: FedEx APIs do not support Cross-Origin Resource Sharing (CORS) mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param xCustomerTransactionId
    *   This transaction Id helps the customers to track the transaction with APIF.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def retrieveAsyncShip(
      authorization: String,
      body: models.FullSchemaGetConfirmedShipmentAsyncResults,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.SHPCResponseVOGetOpenShipmentResults] = {
    send(api.retrieveAsyncShip(authorization, body, xCustomerTransactionId, xLocale))
  }

  /** Validate Shipment Use this endpoint to verify the accuracy of a shipment request prior to actually submitting shipment request. This
    * allow businesses that receive shipping orders from end-user/customers to verify the shipment information prior to submitting a create
    * shipment request to FedEx and printing a label. If for any reason the information needs to be edited or changed, it can be done while
    * the end-user is still available to confirm the changes.<br><br>Note:<ul><li>This is shipment level validation hence supports
    * validation for single piece shipment only.</li><li>Shipment validation is supported for all Express and Ground - Domestic as well as
    * international shipments with all applicable special services. </li><li>Shipment validation is supported for SmartPost and not for
    * Freight LTL shipments.</li></ul><br><br><i>Note: FedEx APIs do not support Cross-Origin Resource Sharing (CORS) mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param body
    *   The request elements required to create a shipment.
    * @param xCustomerTransactionId
    *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
    *   match the request to the reply.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def validateShipment(
      authorization: String,
      body: models.FullSchemaVerifyShipment,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.SHPCResponseVOValidate] = {
    send(api.validateShipment(authorization, body, xCustomerTransactionId, xLocale))
  }

  /** Create Tag FedEx creates and delivers a returnnn shipping label to your customer and collects the item for return. Your customer needs
    * to have the package ready for pickup when the FedEx driver arrives. Use this endpoint to create tag requests for FedEx Express and
    * FedEx Ground shipments.<br><i>Note: FedEx APIs do not support Cross-Origin Resource Sharing (CORS) mechanism.</i>
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
  def createTag(
      authorization: String,
      body: models.FullSchemaCreateTag,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.SHPCResponseVOCreateTag] = {
    send(api.createTag(authorization, body, xCustomerTransactionId, xLocale))
  }

  /** Cancel Tag This endpoint cancels a FedEx Return Tag and the associated pickup for FedEx Express and FedEx Ground shipments if the
    * shipment has not yet been picked up by the courier.<br><i>Note: FedEx APIs do not support Cross-Origin Resource Sharing (CORS)
    * mechanism.</i>
    *
    * @param authorization
    *   This indicates the authorization token for the input request.
    * @param body
    *   The input details required to cancel a tag.
    * @param xCustomerTransactionId
    *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
    *   match the request to the reply.
    * @param xLocale
    *   This indicates the combination of language code and country code. <a onclick='loadDocReference("locales")'>Click here to see
    *   Locales</a>
    */
  def cancelTag(
      authorization: String,
      shipmentid: String,
      body: models.FullSchemaCancelTag,
      xCustomerTransactionId: Option[String] = None,
      xLocale: Option[String] = None
  ): F[models.SHPCResponseVO] = {
    send(api.cancelTag(authorization, shipmentid, body, xCustomerTransactionId, xLocale))
  }
}
