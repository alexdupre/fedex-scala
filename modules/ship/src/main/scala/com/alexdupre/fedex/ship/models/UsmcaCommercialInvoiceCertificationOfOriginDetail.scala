package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The instructions indicating commercial invoice certification of origin.
  *
  * @param customerImageUsages
  *   Specifies the usage and identification of customer supplied images to be used on this document.
  * @param certifierSpecification
  *   This is certifier specification.
  * @param importerSpecification
  *   This is importer specification.
  * @param producerSpecification
  *   This is producer specification.
  * @param certifierJobTitle
  *   Specify the job title of the certifier
  */
case class UsmcaCommercialInvoiceCertificationOfOriginDetail(
    customerImageUsages: Option[Seq[CustomerImageUsage]] = None,
    documentFormat: Option[ShippingDocumentFormat] = None,
    certifierSpecification: Option[UsmcaCommercialInvoiceCertificationOfOriginDetail.CertifierSpecification] = None,
    importerSpecification: Option[UsmcaCommercialInvoiceCertificationOfOriginDetail.ImporterSpecification] = None,
    producerSpecification: Option[UsmcaCommercialInvoiceCertificationOfOriginDetail.ProducerSpecification] = None,
    producer: Option[Party3] = None,
    certifierJobTitle: Option[String] = None
)

object UsmcaCommercialInvoiceCertificationOfOriginDetail {
  enum CertifierSpecification {
    case EXPORTER
    case IMPORTER
    case PRODUCER
    case UNKNOWN_DEFAULT
  }
  object CertifierSpecification {
    given Encoder[CertifierSpecification] = Encoder.encodeString.contramap(_.toString)
    given Decoder[CertifierSpecification] =
      Decoder.decodeString.map(s => scala.util.Try(CertifierSpecification.valueOf(s)).getOrElse(CertifierSpecification.UNKNOWN_DEFAULT))
  }

  enum ProducerSpecification {
    case AVAILABLE_UPON_REQUEST
    case VARIOUS
    case SAME_AS_EXPORTER
    case UNKNOWN_DEFAULT
  }
  object ProducerSpecification {
    given Encoder[ProducerSpecification] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ProducerSpecification] =
      Decoder.decodeString.map(s => scala.util.Try(ProducerSpecification.valueOf(s)).getOrElse(ProducerSpecification.UNKNOWN_DEFAULT))
  }

  enum ImporterSpecification {
    case UNKNOWN
    case VARIOUS
    case UNKNOWN_DEFAULT
  }
  object ImporterSpecification {
    given Encoder[ImporterSpecification] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ImporterSpecification] =
      Decoder.decodeString.map(s => scala.util.Try(ImporterSpecification.valueOf(s)).getOrElse(ImporterSpecification.UNKNOWN_DEFAULT))
  }
  given Encoder[UsmcaCommercialInvoiceCertificationOfOriginDetail] =
    new Encoder.AsObject[UsmcaCommercialInvoiceCertificationOfOriginDetail] {
      final def encodeObject(o: UsmcaCommercialInvoiceCertificationOfOriginDetail): JsonObject = {
        JsonObject.fromIterable(
          Vector(
            "customerImageUsages"    -> o.customerImageUsages.asJson,
            "documentFormat"         -> o.documentFormat.asJson,
            "certifierSpecification" -> o.certifierSpecification.asJson,
            "importerSpecification"  -> o.importerSpecification.asJson,
            "producerSpecification"  -> o.producerSpecification.asJson,
            "producer"               -> o.producer.asJson,
            "certifierJobTitle"      -> o.certifierJobTitle.asJson
          )
        )
      }
    }.mapJson(_.dropNullValues)
  given Decoder[UsmcaCommercialInvoiceCertificationOfOriginDetail] = (c: HCursor) => {
    for {
      customerImageUsages    <- c.downField("customerImageUsages").as[Option[Seq[CustomerImageUsage]]]
      documentFormat         <- c.downField("documentFormat").as[Option[ShippingDocumentFormat]]
      certifierSpecification <- c.downField("certifierSpecification").as[Option[CertifierSpecification]]
      importerSpecification  <- c.downField("importerSpecification").as[Option[ImporterSpecification]]
      producerSpecification  <- c.downField("producerSpecification").as[Option[ProducerSpecification]]
      producer               <- c.downField("producer").as[Option[Party3]]
      certifierJobTitle      <- c.downField("certifierJobTitle").as[Option[String]]
    } yield UsmcaCommercialInvoiceCertificationOfOriginDetail(
      customerImageUsages,
      documentFormat,
      certifierSpecification,
      importerSpecification,
      producerSpecification,
      producer,
      certifierJobTitle
    )
  }
}
