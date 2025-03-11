package com.alexdupre.fedex.authorization

import sttp.client4.{Backend, Request}
import sttp.monad.MonadError
import sttp.monad.syntax.*

/** API Authorization 1.0
  *
  * @param baseUrl
  *   the server base url
  */
class AuthorizationClient[F[_]](baseUrl: String = "https://apis-sandbox.fedex.com")(using backend: Backend[F]) {

  private val api = new AuthorizationAPI(baseUrl)

  private given monad: MonadError[F] = backend.monad

  private def send[T](request: Request[Either[AuthorizationException, T]]): F[T] = {
    backend.send(request).flatMap(_.body.fold(monad.error, monad.unit))
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
  ): F[models.Response] = {
    send(api.apiAuthorization(grantType, clientId, clientSecret, childKey, childSecret))
  }
}
