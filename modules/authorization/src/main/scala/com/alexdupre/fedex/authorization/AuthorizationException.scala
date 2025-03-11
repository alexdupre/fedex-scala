package com.alexdupre.fedex.authorization

import sttp.client4.ResponseException

abstract class AuthorizationException(message: String, cause: ResponseException[Any]) extends RuntimeException(message, cause)
case class AuthorizationAPIException(description: String, model: Any, cause: ResponseException[Any])
    extends AuthorizationException(description, cause)
case class AuthorizationDeserializationException(description: String, body: String, cause: ResponseException[Any])
    extends AuthorizationException(description, cause)
