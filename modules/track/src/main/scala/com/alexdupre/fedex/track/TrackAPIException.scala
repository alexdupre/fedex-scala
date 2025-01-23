package com.alexdupre.fedex.track

import sttp.client4.ResponseException

case class TrackAPIException(description: String, model: Any, cause: ResponseException[Any]) extends RuntimeException(description, cause)
