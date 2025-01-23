package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object for specifying return shipment details.
  *
  * @param returnType
  *   This specifies the return Type. Required to be set to PRINT_RETURN_LABEL for printed return label shipments.For email return label
  *   shipments returnType must be set to PENDING and pendingShipmentDetail must be set to EMAIL.<br>Valid Values : PENDING,
  *   PRINT_RETURN_LABEL<br>Example: PRINT_RETURN_LABEL
  */
case class ReturnShipmentDetail(
    returnType: ReturnShipmentDetail.ReturnType,
    returnEmailDetail: Option[ReturnEmailDetail] = None,
    rma: Option[ReturnMerchandiseAuthorization] = None,
    returnAssociationDetail: Option[ReturnAssociationDetail] = None
)

object ReturnShipmentDetail {
  enum ReturnType {
    case PENDING
    case PRINT_RETURN_LABEL
  }
  object ReturnType {
    given Encoder[ReturnType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ReturnType] = Decoder.decodeString.emapTry(s => scala.util.Try(ReturnType.valueOf(s)))
  }
  given Encoder[ReturnShipmentDetail] = new Encoder.AsObject[ReturnShipmentDetail] {
    final def encodeObject(o: ReturnShipmentDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "returnType"              -> o.returnType.asJson,
          "returnEmailDetail"       -> o.returnEmailDetail.asJson,
          "rma"                     -> o.rma.asJson,
          "returnAssociationDetail" -> o.returnAssociationDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ReturnShipmentDetail] = (c: HCursor) => {
    for {
      returnType              <- c.downField("returnType").as[ReturnType]
      returnEmailDetail       <- c.downField("returnEmailDetail").as[Option[ReturnEmailDetail]]
      rma                     <- c.downField("rma").as[Option[ReturnMerchandiseAuthorization]]
      returnAssociationDetail <- c.downField("returnAssociationDetail").as[Option[ReturnAssociationDetail]]
    } yield ReturnShipmentDetail(returnType, returnEmailDetail, rma, returnAssociationDetail)
  }
}
