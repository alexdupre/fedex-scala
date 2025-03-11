package com.alexdupre.fedex.ship

import sttp.client4.ResponseException

abstract class ShipException(message: String, cause: ResponseException[Any])                extends RuntimeException(message, cause)
case class ShipAPIException(description: String, model: Any, cause: ResponseException[Any]) extends ShipException(description, cause)
case class ShipDeserializationException(description: String, body: String, cause: ResponseException[Any])
    extends ShipException(description, cause)
