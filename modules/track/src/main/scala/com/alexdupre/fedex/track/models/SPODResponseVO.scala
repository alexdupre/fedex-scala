package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** SPODResponseVO Model
  *
  * @param documentType
  *   The types of tracking document.<br> Example: SIGNATURE_PROOF_OF_DELIVERY
  * @param documentFormat
  *   The format of the tracking document. <br>Valid values are PDF and PNG.
  * @param document
  *   This field holds the list of byte array(base64) which specifies the image of the recipient's signature (if the signature is available)
  *   once the shipment has been delivered. <br>Example:
  *   [iVBORw0KGgoAAAANSUhEUgAABX0AAAfECAIAAACJ1ysDAACAAElEQVR42uzdeXxM9/4/8EhC3ZZkskmotlpuq6227r1ur6KqrSXLZLHvhKyIndJy7ZLMlgiy2BJ71E4QewmCICUqJAiCIIgkM3P2M/P7nDkxHQm97b0339b9vZ6P92MeM3P2kX8+L5/FzgQAAAAAAAAAUDvs8BMAAAAAAAAAQC1B7gAAAAAAAAAAtQW5AwAAAAAAAADUFuQOAAAAAAAAAFBbkDsAAAAAAAAAQG1B7gAAAAAAAAAAtQW5AwAAAAAAAADUFuQOAAAAAAAAAFBbkDsAAAAAAAAAQG1B7gAAAAAAAAAAtQW5AwAAAAAAAADUFuQOAAAAAAAAAFBbkDsAAAAAAAAAQG1B7gAAAAAAAAAAtQW5AwAAAAAAAADUFuQOAAAAAAAAAFBbkDsAAAAAAAAAQG1B7gAAAAAAAAAAtQW5AwAAAAAAAADUFuQOAAAAAAAAAFBbkDsAAAAAAAAAQG1B7gAAAAAAAAAAtQW5AwAAAAAAAADUFuQOAAAAAAAAAFBbkDsAAAAAAAAAQG1B7gAAAAAAAAAAtQW5AwAAAAAAAADUFuQOAAAAAAAAAFBbkDsAAAAAAAAAQG1B7gAAAAAAAAAAtQW5AwAAAAAAAADUFuQO/6bl0/+dhCEfLh7eOonUsE8S5RouFfly8YD34od8lKAK23ruUKGhXO7v8CtzB1Mt5A6FP+cOLuRxrLmDLtBD1d9rtmWcxRf6Yba5g6MlenAwRdTlo5zoyU2N0/5snPYe9d179Hfvst+1YL9rznwrFT2pKf1tCyqlN3V8BVOcKxjLkDsAAAAAAAAgd4B/J3dQukiv/d5eGPH3ZdMCNySO27du/vH10cdXzzm6ek6Wba345+HUWT/sXZ17K/8uYzSIAmey5A6m58cKctwgWsry/tmbeXGL+l+nFKKcOwyTcodeTeOUUu6gepo7aAM9YmrmDqawuqYw6VUMqy+MUHD//J]
  * @param alerts
  *   Specifies the alert received when the recipient's signature has been taken as a proof of shipment delivery.<br>Example:
  *   TRACKING.DATA.NOTFOUND - Tracking data unavailable
  */
case class SPODResponseVO(
    localization: Option[Localization] = None,
    documentType: Option[String] = None,
    documentFormat: Option[String] = None,
    document: Option[Seq[String]] = None,
    alerts: Option[Seq[Alert]] = None
)

object SPODResponseVO {

  given Encoder[SPODResponseVO] = new Encoder.AsObject[SPODResponseVO] {
    final def encodeObject(o: SPODResponseVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "localization"   -> o.localization.asJson,
          "documentType"   -> o.documentType.asJson,
          "documentFormat" -> o.documentFormat.asJson,
          "document"       -> o.document.asJson,
          "alerts"         -> o.alerts.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[SPODResponseVO] = (c: HCursor) => {
    for {
      localization   <- c.downField("localization").as[Option[Localization]]
      documentType   <- c.downField("documentType").as[Option[String]]
      documentFormat <- c.downField("documentFormat").as[Option[String]]
      document       <- c.downField("document").as[Option[Seq[String]]]
      alerts         <- c.downField("alerts").as[Option[Seq[Alert]]]
    } yield SPODResponseVO(localization, documentType, documentFormat, document, alerts)
  }
}
