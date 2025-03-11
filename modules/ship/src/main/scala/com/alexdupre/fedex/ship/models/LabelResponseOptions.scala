package com.alexdupre.fedex.ship.models

import io.circe.{Decoder, Encoder}

enum LabelResponseOptions {
  case URL_ONLY
  case LABEL
  case UNKNOWN_DEFAULT
}

object LabelResponseOptions {
  given Encoder[LabelResponseOptions] = Encoder.encodeString.contramap(_.toString)
  given Decoder[LabelResponseOptions] =
    Decoder.decodeString.map(s => scala.util.Try(LabelResponseOptions.valueOf(s)).getOrElse(LabelResponseOptions.UNKNOWN_DEFAULT))
}
