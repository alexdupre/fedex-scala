package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are billing details.
  *
  * @param billingCode
  *   Indicates a billing code.
  * @param billingType
  *   These are duties and taxes billing type.
  * @param aliasId
  *   This is bill to alias identifier.
  * @param accountNickname
  *   This is account nick name.
  * @param accountNumber
  *   This is bill to account number.
  * @param accountNumberCountryCode
  *   This is the country code of the account number.<br>Example: CA
  */
case class BillingDetails(
    billingCode: Option[String] = None,
    billingType: Option[String] = None,
    aliasId: Option[String] = None,
    accountNickname: Option[String] = None,
    accountNumber: Option[String] = None,
    accountNumberCountryCode: Option[String] = None
)

object BillingDetails {

  given Encoder[BillingDetails] = new Encoder.AsObject[BillingDetails] {
    final def encodeObject(o: BillingDetails): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "billingCode"              -> o.billingCode.asJson,
          "billingType"              -> o.billingType.asJson,
          "aliasId"                  -> o.aliasId.asJson,
          "accountNickname"          -> o.accountNickname.asJson,
          "accountNumber"            -> o.accountNumber.asJson,
          "accountNumberCountryCode" -> o.accountNumberCountryCode.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[BillingDetails] = (c: HCursor) => {
    for {
      billingCode              <- c.downField("billingCode").as[Option[String]]
      billingType              <- c.downField("billingType").as[Option[String]]
      aliasId                  <- c.downField("aliasId").as[Option[String]]
      accountNickname          <- c.downField("accountNickname").as[Option[String]]
      accountNumber            <- c.downField("accountNumber").as[Option[String]]
      accountNumberCountryCode <- c.downField("accountNumberCountryCode").as[Option[String]]
    } yield BillingDetails(billingCode, billingType, aliasId, accountNickname, accountNumber, accountNumberCountryCode)
  }
}
