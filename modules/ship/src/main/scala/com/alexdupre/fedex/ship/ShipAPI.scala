package com.alexdupre.fedex.ship

import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import sttp.client4.circe.*
import sttp.client4.*

/** Ship API 1.0.0
  *
  * @param baseUrl
  *   the server base url
  */
class ShipAPI(baseUrl: String = "https://apis-sandbox.fedex.com") {
  private def cleanAndStringify(in: Map[String, Any]): Map[String, String] = {
    in.flatMap {
      case (_, None)          => None
      case (key, Some(value)) => Some(key -> value.toString)
      case (key, value)       => Some(key -> value.toString)
    }
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
  ): Request[Either[ShipAPIException, models.SHPCResponseVOShipShipment]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .post(uri"$baseUrl/ship/v1/shipments")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.SHPCResponseVOShipShipment].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO])
            .fold(e => throw e, o => ShipAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401])
            .fold(e => throw e, o => ShipAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403])
            .fold(e => throw e, o => ShipAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404])
            .fold(e => throw e, o => ShipAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500])
            .fold(e => throw e, o => ShipAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503])
            .fold(e => throw e, o => ShipAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
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
  ): Request[Either[ShipAPIException, models.SHPCResponseVOCancelShipment]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .put(uri"$baseUrl/ship/v1/shipments/cancel")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.SHPCResponseVOCancelShipment].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO2])
            .fold(e => throw e, o => ShipAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401_2])
            .fold(e => throw e, o => ShipAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403_2])
            .fold(e => throw e, o => ShipAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404_2])
            .fold(e => throw e, o => ShipAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500_2])
            .fold(e => throw e, o => ShipAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503_2])
            .fold(e => throw e, o => ShipAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
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
  ): Request[Either[ShipAPIException, models.SHPCResponseVOGetOpenShipmentResults]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .post(uri"$baseUrl/ship/v1/shipments/results")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.SHPCResponseVOGetOpenShipmentResults].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO2])
            .fold(e => throw e, o => ShipAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401_2])
            .fold(e => throw e, o => ShipAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403_2])
            .fold(e => throw e, o => ShipAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404_2])
            .fold(e => throw e, o => ShipAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500_2])
            .fold(e => throw e, o => ShipAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503_2])
            .fold(e => throw e, o => ShipAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
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
  ): Request[Either[ShipAPIException, models.SHPCResponseVOValidate]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .post(uri"$baseUrl/ship/v1/shipments/packages/validate")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.SHPCResponseVOValidate].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO2])
            .fold(e => throw e, o => ShipAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO2])
            .fold(e => throw e, o => ShipAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO2])
            .fold(e => throw e, o => ShipAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .fold(e => throw e, o => ShipAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO2])
            .fold(e => throw e, o => ShipAPIException("Failure", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
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
  ): Request[Either[ShipAPIException, models.SHPCResponseVOCreateTag]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .post(uri"$baseUrl/ship/v1/shipments/tag")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.SHPCResponseVOCreateTag].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO])
            .fold(e => throw e, o => ShipAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401])
            .fold(e => throw e, o => ShipAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403])
            .fold(e => throw e, o => ShipAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404])
            .fold(e => throw e, o => ShipAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500])
            .fold(e => throw e, o => ShipAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503])
            .fold(e => throw e, o => ShipAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
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
  ): Request[Either[ShipAPIException, models.SHPCResponseVO]] = {
    val __body = body
    val __headers = Map(
      "x-customer-transaction-id" -> xCustomerTransactionId,
      "x-locale"                  -> xLocale,
      "authorization"             -> authorization
    )
    basicRequest
      .put(uri"$baseUrl/ship/v1/shipments/tag/cancel/$shipmentid")
      .headers(cleanAndStringify(__headers))
      .body(asJson(__body))
      .response(asJson[models.SHPCResponseVO].mapLeft {
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 400 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO2])
            .fold(e => throw e, o => ShipAPIException("Bad Request", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 401 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO401_2])
            .fold(e => throw e, o => ShipAPIException("Unauthorized", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 403 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO403_2])
            .fold(e => throw e, o => ShipAPIException("Forbidden", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 404 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO404_2])
            .fold(e => throw e, o => ShipAPIException("Not Found", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 500 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO500_2])
            .fold(e => throw e, o => ShipAPIException("Failure", o, ex))
        case ex @ ResponseException.UnexpectedStatusCode(body, response) if response.code.code == 503 =>
          parse(body.toString)
            .flatMap(_.as[models.ErrorResponseVO503_2])
            .fold(e => throw e, o => ShipAPIException("Service Unavailable", o, ex))
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
  }

}

object ShipAPI {
  object Servers {
    val SandboxServer    = "https://apis-sandbox.fedex.com"
    val ProductionServer = "https://apis.fedex.com"
  }
}
