package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The request elements required to create a shipment.
  *
  * @param mergeLabelDocOption
  *   It specifies the content of the merged pdf URL in the response. The merged pdf URL is generated only if the labelResponseOption is
  *   indicated as URL_ONLY.<ul><li>If the value is 'LABELS_AND_DOCS', then merged (all shipping labels and shipping documents) pdf URL will
  *   be returned.</li><li>If the value is 'LABELS_ONLY', merged (all shipping labels only) pdf URL will be returned.</li><li>If the value
  *   is 'NONE', then no merged pdf URL will be returned.</li></ul><br>This is optional field and will default to LABELS_AND_DOCS.<br>Note:
  *   If the value is 'LABELS_ONLY', then the returned merged pdf label will not be in the Base64 encoded format.
  * @param oneLabelAtATime
  *   This flag is used to specify if the shipment is singleshot mps or one Label at a time, piece by piece shipment. Default is false. If
  *   true, one label at a time is processed.
  */
case class FullSchemaShip(
    requestedShipment: RequestedShipment,
    labelResponseOptions: LabelResponseOptions,
    accountNumber: ShipperAccountNumber,
    mergeLabelDocOption: Option[FullSchemaShip.MergeLabelDocOption] = None,
    shipAction: Option[OpenShipmentAction] = None,
    processingOptionType: Option[AsynchronousProcessingOptionType1] = None,
    oneLabelAtATime: Option[Boolean] = None
)

object FullSchemaShip {
  enum MergeLabelDocOption {
    case NONE
    case LABELS_AND_DOCS
    case LABELS_ONLY
    case UNKNOWN_DEFAULT
  }
  object MergeLabelDocOption {
    given Encoder[MergeLabelDocOption] = Encoder.encodeString.contramap(_.toString)
    given Decoder[MergeLabelDocOption] =
      Decoder.decodeString.map(s => scala.util.Try(MergeLabelDocOption.valueOf(s)).getOrElse(MergeLabelDocOption.UNKNOWN_DEFAULT))
  }
  given Encoder[FullSchemaShip] = new Encoder.AsObject[FullSchemaShip] {
    final def encodeObject(o: FullSchemaShip): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "requestedShipment"    -> o.requestedShipment.asJson,
          "labelResponseOptions" -> o.labelResponseOptions.asJson,
          "accountNumber"        -> o.accountNumber.asJson,
          "mergeLabelDocOption"  -> o.mergeLabelDocOption.asJson,
          "shipAction"           -> o.shipAction.asJson,
          "processingOptionType" -> o.processingOptionType.asJson,
          "oneLabelAtATime"      -> o.oneLabelAtATime.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaShip] = (c: HCursor) => {
    for {
      requestedShipment    <- c.downField("requestedShipment").as[RequestedShipment]
      labelResponseOptions <- c.downField("labelResponseOptions").as[LabelResponseOptions]
      accountNumber        <- c.downField("accountNumber").as[ShipperAccountNumber]
      mergeLabelDocOption  <- c.downField("mergeLabelDocOption").as[Option[MergeLabelDocOption]]
      shipAction           <- c.downField("shipAction").as[Option[OpenShipmentAction]]
      processingOptionType <- c.downField("processingOptionType").as[Option[AsynchronousProcessingOptionType1]]
      oneLabelAtATime      <- c.downField("oneLabelAtATime").as[Option[Boolean]]
    } yield FullSchemaShip(
      requestedShipment,
      labelResponseOptions,
      accountNumber,
      mergeLabelDocOption,
      shipAction,
      processingOptionType,
      oneLabelAtATime
    )
  }
}
