package com.alexdupre.fedex.authorization.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is the response of OAuth token and having access token details.
  *
  * @param accessToken
  *   This is an encrypted OAuth token used to authenticate your API requests. Use it in the authorization header of your API
  *   requests.<br>Example: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpX……
  * @param tokenType
  *   This is a token type. In this case, it is 'bearer authentication'.
  * @param expiresIn
  *   Indicates the token expiration time in seconds. The standard token expiration time is one hour. <br>Example: 3600
  * @param scope
  *   Indicates the scope of authorization provided to the consumer.<br> Example: CXS
  */
case class Response(
    accessToken: Option[String] = None,
    tokenType: Option[String] = None,
    expiresIn: Option[Int] = None,
    scope: Option[String] = None
)

object Response {

  given Encoder[Response] = new Encoder.AsObject[Response] {
    final def encodeObject(o: Response): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "access_token" -> o.accessToken.asJson,
          "token_type"   -> o.tokenType.asJson,
          "expires_in"   -> o.expiresIn.asJson,
          "scope"        -> o.scope.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Response] = (c: HCursor) => {
    for {
      accessToken <- c.downField("access_token").as[Option[String]]
      tokenType   <- c.downField("token_type").as[Option[String]]
      expiresIn   <- c.downField("expires_in").as[Option[Int]]
      scope       <- c.downField("scope").as[Option[String]]
    } yield Response(accessToken, tokenType, expiresIn, scope)
  }
}
