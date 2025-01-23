package com.alexdupre.fedex.ship.models

import io.circe.{Decoder, Encoder}

enum AsynchronousProcessingOptionType1 {
  case SYNCHRONOUS_ONLY
  case ALLOW_ASYNCHRONOUS
}

object AsynchronousProcessingOptionType1 {
  given Encoder[AsynchronousProcessingOptionType1] = Encoder.encodeString.contramap(_.toString)
  given Decoder[AsynchronousProcessingOptionType1] =
    Decoder.decodeString.emapTry(s => scala.util.Try(AsynchronousProcessingOptionType1.valueOf(s)))
}
