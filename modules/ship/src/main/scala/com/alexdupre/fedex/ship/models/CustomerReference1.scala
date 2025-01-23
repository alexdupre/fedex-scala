package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** CustomerReference_1 Model
  *
  * @param customerReferenceType
  *   This is a customer reference type. The value specified here for the element is printed on the Commercial Invoice only for tracking and
  *   label information.<p>Note: <ul><li>The P_O_NUMBER value must be specified in customerReferences under
  *   requestedPackageLineItems</li><li>The INVOICE_NUMBER value that is printed on the FedEx-supplied invoice must be specified in
  *   customerReferences under commercialInvoice. Value defined in this section will print on the label that is attached to the
  *   package</li><li>The RMA_ASSOCIATION value sent by the customer is returned on the label in human readable form but also as a
  *   barcode.</li></ul>Note: INTRACOUNTRY_REGULATORY_REFERENCE is applicable only in Intra-Brazil.<br><a
  *   onclick='loadDocReference("customerreferencetypes")'>For more information, click here for Customer References</a>.<br>Example:
  *   DEPARTMENT_NUMBER
  * @param value
  *   This is a customer reference type value.<br>Example: 3686 <ul><li>The P_O_NUMBER value must be specified in customerReferences under
  *   requestedPackageLineItems</li><li>The INVOICE_NUMBER value that is printed on the FedEx-supplied invoice must be specified in
  *   customerReferences under commercialInvoice. Value defined in this section will print on the label that is attached to the
  *   package</li><li>The RMA value sent by the customer is returned on the label in human readable form but also as a barcode.
  *   RMA_ASSOCIATION only prints on the label as a barcode for a Return shipment.</ul>NOTE:<ul><li> INTRACOUNTRY_REGULATORY_REFERENCE is
  *   applicable only in Intra-Brazil.</li><li> Maximum length varies for value field depending on customerReferenceType.</li></ul> Maximum
  *   length for value is as follows: <ul><li>CUSTOMER_REFERENCE - 40(Express), 30(Ground)</li><li>DEPARTMENT_NUMBER -
  *   30</li><li>INVOICE_NUMBER - 30</li><li>P_O_NUMBER - 30</li><li>INTRACOUNTRY_REGULATORY_REFERENCE - 30</li><li>RMA_ASSOCIATION -
  *   20</li>
  */
case class CustomerReference1(
    customerReferenceType: Option[CustomerReference1.CustomerReferenceType] = None,
    value: Option[String] = None
)

object CustomerReference1 {
  enum CustomerReferenceType {
    case CUSTOMER_REFERENCE
    case DEPARTMENT_NUMBER
    case INVOICE_NUMBER
    case P_O_NUMBER
    case INTRACOUNTRY_REGULATORY_REFERENCE
    case RMA_ASSOCIATION
  }
  object CustomerReferenceType {
    given Encoder[CustomerReferenceType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[CustomerReferenceType] = Decoder.decodeString.emapTry(s => scala.util.Try(CustomerReferenceType.valueOf(s)))
  }
  given Encoder[CustomerReference1] = new Encoder.AsObject[CustomerReference1] {
    final def encodeObject(o: CustomerReference1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "customerReferenceType" -> o.customerReferenceType.asJson,
          "value"                 -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CustomerReference1] = (c: HCursor) => {
    for {
      customerReferenceType <- c.downField("customerReferenceType").as[Option[CustomerReferenceType]]
      value                 <- c.downField("value").as[Option[String]]
    } yield CustomerReference1(customerReferenceType, value)
  }
}
