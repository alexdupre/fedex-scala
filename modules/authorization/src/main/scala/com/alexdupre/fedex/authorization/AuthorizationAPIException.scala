package com.alexdupre.fedex.authorization

import sttp.client4.ResponseException

case class AuthorizationAPIException(description: String, model: Any, cause: ResponseException[Any])
    extends RuntimeException(description, cause)
