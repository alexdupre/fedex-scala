package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specify the usse and identification of supplied images to be used on this document.
  *
  * @param id
  *   Specify the Image ID.<br>Example:IMAGE_5
  * @param `type`
  *   Specify Customer Image Type.<br>Example:SIGNATURE
  * @param providedImageType
  *   Provided Image Type<br>Example: SIGNATURE
  */
case class CustomerImageUsage(
    id: Option[CustomerImageUsage.Id] = None,
    `type`: Option[CustomerImageUsage.Type] = None,
    providedImageType: Option[CustomerImageUsage.ProvidedImageType] = None
)

object CustomerImageUsage {
  enum ProvidedImageType {
    case LETTER_HEAD
    case SIGNATURE
  }
  object ProvidedImageType {
    given Encoder[ProvidedImageType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ProvidedImageType] = Decoder.decodeString.emapTry(s => scala.util.Try(ProvidedImageType.valueOf(s)))
  }

  enum Id {
    case IMAGE_1
    case IMAGE_2
    case IMAGE_3
    case IMAGE_4
    case IMAGE_5
  }
  object Id {
    given Encoder[Id] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Id] = Decoder.decodeString.emapTry(s => scala.util.Try(Id.valueOf(s)))
  }

  enum Type {
    case SIGNATURE
    case LETTER_HEAD
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.emapTry(s => scala.util.Try(Type.valueOf(s)))
  }
  given Encoder[CustomerImageUsage] = new Encoder.AsObject[CustomerImageUsage] {
    final def encodeObject(o: CustomerImageUsage): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "id"                -> o.id.asJson,
          "type"              -> o.`type`.asJson,
          "providedImageType" -> o.providedImageType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CustomerImageUsage] = (c: HCursor) => {
    for {
      id                <- c.downField("id").as[Option[Id]]
      `type`            <- c.downField("type").as[Option[Type]]
      providedImageType <- c.downField("providedImageType").as[Option[ProvidedImageType]]
    } yield CustomerImageUsage(id, `type`, providedImageType)
  }
}
