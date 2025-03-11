package com.alexdupre.fedex.authorization

import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import sttp.client4.circe.*
import sttp.client4.*

/** API Authorization 1.0
  *
  * @param baseUrl
  *   the server base url
  */
class AuthorizationAPI(baseUrl: String = "https://apis-sandbox.fedex.com") {
  private def cleanAndStringify(in: Map[String, Any]): Map[String, String] = {
    in.flatMap {
      case (_, None)          => None
      case (key, Some(value)) => Some(key -> value.toString)
      case (key, value)       => Some(key -> value.toString)
    }
  }

  private def mapAPIError[T: Decoder](description: String, ex: ResponseException.UnexpectedStatusCode[_]) = {
    parse(ex.body.toString)
      .flatMap(_.as[T])
      .fold(
        e => AuthorizationDeserializationException("Deserialization Error: " + e.getMessage, ex.body.toString, ex),
        o => AuthorizationAPIException(description, o, ex)
      )
  }

  /** API Authorization Use this endpoint to request the OAuth token (bearer token) to authorize your application to access FedEx resources.
    * You can pass this bearer token in your subsequent individual FedEx API endpoint requests.<br/><i>Note: FedEx APIs do not support
    * Cross-Origin Resource Sharing (CORS) mechanism.</i>
    *
    * @param grantType
    *   Specify Type of customer requesting the Oauth token.<br>Valid Values: client_credentials, csp_credentials,
    *   client_pc_credentials<br>Note:<br>client_credentials - should be used for customers, Integrators, and brand new Compatible Provider
    *   customers who are yet to unboard child accounts.<br>csp_credentials - should be used for Integrators, and Compatible Provider
    *   customers with existing child accounts.<br>client_pc_credentials – should be used for Proprietary Parent Child customers.
    * @param clientId
    *   Specify the Client ID also known as API Key received during FedEx Developer portal registration.<br>Example: XXXX-XXX-XXXX-XXX
    * @param clientSecret
    *   Specify the Client secret also known as Secret Key received during FedEx Developer portal registration.<br>Example:
    *   XXXX-XXX-XXXX-XXX
    * @param childKey
    *   Specify the Client ID also known as Customer Key. This element is used as a login credential for an Integrator customer, Compatible
    *   customer or a Proprietary Parent Child customer to access the application on behalf of their customer.<br>Example:
    *   XXXX-XXX-XXXX-XXX<br>Note: This element should be used by Integrator, Compatible and Proprietary Parent Child customers.
    * @param childSecret
    *   Specify the Client secret also known as Customer Secret. This element is used as a login credential for an Integrator customer,
    *   Compatible customer or a Proprietary Parent Child customer to access the application on behalf of their customer.<br>Example:
    *   XXXX-XXX-XXXX-XXX<br>Note: This element should be used by Integrator, Compatible and Proprietary Parent Child customers.
    */
  def apiAuthorization(
      grantType: String,
      clientId: String,
      clientSecret: String,
      childKey: Option[String] = None,
      childSecret: Option[String] = None
  ): Request[Either[AuthorizationException, models.Response]] = {
    val __body = Map(
      "grant_type"    -> grantType,
      "client_id"     -> clientId,
      "client_secret" -> clientSecret,
      "child_Key"     -> childKey,
      "child_secret"  -> childSecret
    )
    val __headers = Map.empty[String, Any]
    basicRequest
      .post(uri"$baseUrl/oauth/token")
      .headers(cleanAndStringify(__headers))
      .body(cleanAndStringify(__body))
      .response(asJson[models.Response].mapLeft {
        case ex @ ResponseException.DeserializationException(body, cause, response) =>
          AuthorizationDeserializationException("Deserialization Error: " + ex.getMessage, body, ex)
        case ex @ ResponseException.UnexpectedStatusCode(_, response) if response.code.code == 401 =>
          mapAPIError[models.ErrorResponseVO]("Unauthorized", ex)
        case ex @ ResponseException.UnexpectedStatusCode(_, response) if response.code.code == 500 =>
          mapAPIError[models.ErrorResponseVO]("Failure", ex)
        case ex @ ResponseException.UnexpectedStatusCode(_, response) if response.code.code == 503 =>
          mapAPIError[models.ErrorResponseVO]("Service Unavailable", ex)
        case ex: Throwable => throw ex
        case unexpected    => sys.error("Unexpected error: " + unexpected)
      })
  }

}

object AuthorizationAPI {
  object Servers {
    val SandboxServer    = "https://apis-sandbox.fedex.com"
    val ProductionServer = "https://apis.fedex.com"
  }
}
