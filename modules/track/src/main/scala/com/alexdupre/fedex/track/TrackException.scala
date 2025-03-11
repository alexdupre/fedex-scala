package com.alexdupre.fedex.track

import sttp.client4.ResponseException

abstract class TrackException(message: String, cause: ResponseException[Any])                extends RuntimeException(message, cause)
case class TrackAPIException(description: String, model: Any, cause: ResponseException[Any]) extends TrackException(description, cause)
case class TrackDeserializationException(description: String, body: String, cause: ResponseException[Any])
    extends TrackException(description, cause)
