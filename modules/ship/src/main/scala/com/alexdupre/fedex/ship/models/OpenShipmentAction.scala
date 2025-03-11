package com.alexdupre.fedex.ship.models

import io.circe.{Decoder, Encoder}

enum OpenShipmentAction {
  case CONFIRM
  case TRANSFER
  case UNKNOWN_DEFAULT
}

object OpenShipmentAction {
  given Encoder[OpenShipmentAction] = Encoder.encodeString.contramap(_.toString)
  given Decoder[OpenShipmentAction] =
    Decoder.decodeString.map(s => scala.util.Try(OpenShipmentAction.valueOf(s)).getOrElse(OpenShipmentAction.UNKNOWN_DEFAULT))
}
