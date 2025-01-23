package com.alexdupre.fedex.ship.models

import io.circe.{Decoder, Encoder}

enum OpenShipmentAction {
  case CONFIRM
  case TRANSFER
}

object OpenShipmentAction {
  given Encoder[OpenShipmentAction] = Encoder.encodeString.contramap(_.toString)
  given Decoder[OpenShipmentAction] = Decoder.decodeString.emapTry(s => scala.util.Try(OpenShipmentAction.valueOf(s)))
}
