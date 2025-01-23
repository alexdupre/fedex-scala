package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** FullSchema-getConfirmedShipmentAsyncResults Model
  *
  * @param jobId
  *   Indicates the job under which the deferred shipment artifacts must be identified in the subsequent retrieval request. <br> Example:
  *   89sxxxxx233ae24ff31xxxxx
  */
case class FullSchemaGetConfirmedShipmentAsyncResults(
    accountNumber: AccountNumber,
    jobId: String
)

object FullSchemaGetConfirmedShipmentAsyncResults {

  given Encoder[FullSchemaGetConfirmedShipmentAsyncResults] = new Encoder.AsObject[FullSchemaGetConfirmedShipmentAsyncResults] {
    final def encodeObject(o: FullSchemaGetConfirmedShipmentAsyncResults): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "accountNumber" -> o.accountNumber.asJson,
          "jobId"         -> o.jobId.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaGetConfirmedShipmentAsyncResults] = (c: HCursor) => {
    for {
      accountNumber <- c.downField("accountNumber").as[AccountNumber]
      jobId         <- c.downField("jobId").as[String]
    } yield FullSchemaGetConfirmedShipmentAsyncResults(accountNumber, jobId)
  }
}
