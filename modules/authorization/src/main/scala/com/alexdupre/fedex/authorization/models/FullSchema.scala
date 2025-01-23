package com.alexdupre.fedex.authorization.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The request elements for OAuth API.
  *
  * @param grantType
  *   Specify Type of customer requesting the Oauth token.<br>Valid Values: client_credentials, csp_credentials,
  *   client_pc_credentials<br>Note:<br>client_credentials - should be used for customers, Integrators, and brand new Compatible Provider
  *   customers who are yet to unboard child accounts.<br>csp_credentials - should be used for Integrators, and Compatible Provider
  *   customers with existing child accounts.<br>client_pc_credentials – should be used for Proprietary Parent Child customers.
  * @param clientId
  *   Specify the Client ID also known as API Key received during FedEx Developer portal registration.<br>Example: XXXX-XXX-XXXX-XXX
  * @param clientSecret
  *   Specify the Client secret also known as Secret Key received during FedEx Developer portal registration.<br>Example: XXXX-XXX-XXXX-XXX
  * @param childKey
  *   Specify the Client ID also known as Customer Key. This element is used as a login credential for an Integrator customer, Compatible
  *   customer or a Proprietary Parent Child customer to access the application on behalf of their customer.<br>Example:
  *   XXXX-XXX-XXXX-XXX<br>Note: This element should be used by Integrator, Compatible and Proprietary Parent Child customers.
  * @param childSecret
  *   Specify the Client secret also known as Customer Secret. This element is used as a login credential for an Integrator customer,
  *   Compatible customer or a Proprietary Parent Child customer to access the application on behalf of their customer.<br>Example:
  *   XXXX-XXX-XXXX-XXX<br>Note: This element should be used by Integrator, Compatible and Proprietary Parent Child customers.
  */
case class FullSchema(
    grantType: String,
    clientId: String,
    clientSecret: String,
    childKey: Option[String] = None,
    childSecret: Option[String] = None
)

object FullSchema {

  given Encoder[FullSchema] = new Encoder.AsObject[FullSchema] {
    final def encodeObject(o: FullSchema): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "grant_type"    -> o.grantType.asJson,
          "client_id"     -> o.clientId.asJson,
          "client_secret" -> o.clientSecret.asJson,
          "child_Key"     -> o.childKey.asJson,
          "child_secret"  -> o.childSecret.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchema] = (c: HCursor) => {
    for {
      grantType    <- c.downField("grant_type").as[String]
      clientId     <- c.downField("client_id").as[String]
      clientSecret <- c.downField("client_secret").as[String]
      childKey     <- c.downField("child_Key").as[Option[String]]
      childSecret  <- c.downField("child_secret").as[Option[String]]
    } yield FullSchema(grantType, clientId, clientSecret, childKey, childSecret)
  }
}
