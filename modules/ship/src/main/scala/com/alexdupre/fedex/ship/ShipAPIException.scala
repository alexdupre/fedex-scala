package com.alexdupre.fedex.ship

import sttp.client4.ResponseException

case class ShipAPIException(description: String, model: Any, cause: ResponseException[Any]) extends RuntimeException(description, cause)
