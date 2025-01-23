package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The instructions indicating how to print the USMCA Certificate of Origin (e.g. whether or not to include the instructions, image type,
  * etc ...).
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
case class UsmcaCertificationOfOriginDetail(
    customerImageUsages: Option[Seq[CustomerImageUsage]] = None,
    documentFormat: Option[ShippingDocumentFormat] = None,
    certifierSpecification: Option[UsmcaCertificationOfOriginDetail.CertifierSpecification] = None,
    importerSpecification: Option[UsmcaCertificationOfOriginDetail.ImporterSpecification] = None,
    producerSpecification: Option[UsmcaCertificationOfOriginDetail.ProducerSpecification] = None,
    producer: Option[Party3] = None,
    blanketPeriod: Option[RetrieveDateRange] = None,
    certifierJobTitle: Option[String] = None
)

object UsmcaCertificationOfOriginDetail {
  enum CertifierSpecification {
    case EXPORTER
    case IMPORTER
    case PRODUCER
  }
  object CertifierSpecification {
    given Encoder[CertifierSpecification] = Encoder.encodeString.contramap(_.toString)
    given Decoder[CertifierSpecification] = Decoder.decodeString.emapTry(s => scala.util.Try(CertifierSpecification.valueOf(s)))
  }

  enum ProducerSpecification {
    case AVAILABLE_UPON_REQUEST
    case VARIOUS
    case SAME_AS_EXPORTER
  }
  object ProducerSpecification {
    given Encoder[ProducerSpecification] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ProducerSpecification] = Decoder.decodeString.emapTry(s => scala.util.Try(ProducerSpecification.valueOf(s)))
  }

  enum ImporterSpecification {
    case UNKNOWN
    case VARIOUS
  }
  object ImporterSpecification {
    given Encoder[ImporterSpecification] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ImporterSpecification] = Decoder.decodeString.emapTry(s => scala.util.Try(ImporterSpecification.valueOf(s)))
  }
  given Encoder[UsmcaCertificationOfOriginDetail] = new Encoder.AsObject[UsmcaCertificationOfOriginDetail] {
    final def encodeObject(o: UsmcaCertificationOfOriginDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "customerImageUsages"    -> o.customerImageUsages.asJson,
          "documentFormat"         -> o.documentFormat.asJson,
          "certifierSpecification" -> o.certifierSpecification.asJson,
          "importerSpecification"  -> o.importerSpecification.asJson,
          "producerSpecification"  -> o.producerSpecification.asJson,
          "producer"               -> o.producer.asJson,
          "blanketPeriod"          -> o.blanketPeriod.asJson,
          "certifierJobTitle"      -> o.certifierJobTitle.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[UsmcaCertificationOfOriginDetail] = (c: HCursor) => {
    for {
      customerImageUsages    <- c.downField("customerImageUsages").as[Option[Seq[CustomerImageUsage]]]
      documentFormat         <- c.downField("documentFormat").as[Option[ShippingDocumentFormat]]
      certifierSpecification <- c.downField("certifierSpecification").as[Option[CertifierSpecification]]
      importerSpecification  <- c.downField("importerSpecification").as[Option[ImporterSpecification]]
      producerSpecification  <- c.downField("producerSpecification").as[Option[ProducerSpecification]]
      producer               <- c.downField("producer").as[Option[Party3]]
      blanketPeriod          <- c.downField("blanketPeriod").as[Option[RetrieveDateRange]]
      certifierJobTitle      <- c.downField("certifierJobTitle").as[Option[String]]
    } yield UsmcaCertificationOfOriginDetail(
      customerImageUsages,
      documentFormat,
      certifierSpecification,
      importerSpecification,
      producerSpecification,
      producer,
      blanketPeriod,
      certifierJobTitle
    )
  }
}
