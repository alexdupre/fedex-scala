package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Field which holds status code of the track information for the shipment being tracked.
  *
  * @param reason
  *   Field which holds Reason code associated to the status of the shipment being tracked.<br> Example: 15,84,IN001,015A,02
  * @param reasonDescription
  *   Field which holds Reason description associated to the status of the shipment being tracked.<br> Example: Customer not available or
  *   business closed,Local delivery restriction, delivery not attempted,Package delivered to recipient address - release authorized
  * @param action
  *   Field which holds recommended action for customer to resolve reason.<br> Example: Contact us at
  *   <http://www.fedex.com/us/customersupport/call/index.html> to discuss possible delivery or pickup alternatives.
  * @param actionDescription
  *   Field which holds recommended action description for customer to resolve reason.<br> Example: Customer not Available or Business
  *   Closed
  */
case class StatusAncillaryDetail(
    reason: Option[String] = None,
    reasonDescription: Option[String] = None,
    action: Option[String] = None,
    actionDescription: Option[String] = None
)

object StatusAncillaryDetail {

  given Encoder[StatusAncillaryDetail] = new Encoder.AsObject[StatusAncillaryDetail] {
    final def encodeObject(o: StatusAncillaryDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "reason"            -> o.reason.asJson,
          "reasonDescription" -> o.reasonDescription.asJson,
          "action"            -> o.action.asJson,
          "actionDescription" -> o.actionDescription.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[StatusAncillaryDetail] = (c: HCursor) => {
    for {
      reason            <- c.downField("reason").as[Option[String]]
      reasonDescription <- c.downField("reasonDescription").as[Option[String]]
      action            <- c.downField("action").as[Option[String]]
      actionDescription <- c.downField("actionDescription").as[Option[String]]
    } yield StatusAncillaryDetail(reason, reasonDescription, action, actionDescription)
  }
}
