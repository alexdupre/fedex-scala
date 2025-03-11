package com.alexdupre.fedex.ship.models

import io.circe.{Decoder, Encoder}

enum AsynchronousProcessingOptionType1 {
  case SYNCHRONOUS_ONLY
  case ALLOW_ASYNCHRONOUS
  case UNKNOWN_DEFAULT
}

object AsynchronousProcessingOptionType1 {
  given Encoder[AsynchronousProcessingOptionType1] = Encoder.encodeString.contramap(_.toString)
  given Decoder[AsynchronousProcessingOptionType1] = Decoder.decodeString.map(s =>
    scala.util.Try(AsynchronousProcessingOptionType1.valueOf(s)).getOrElse(AsynchronousProcessingOptionType1.UNKNOWN_DEFAULT)
  )
}
