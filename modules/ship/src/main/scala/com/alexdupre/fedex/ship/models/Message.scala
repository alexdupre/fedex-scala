package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the advisory details.
  *
  * @param code
  *   Specifies the message code for the tag created.<br>Example: code
  * @param text
  *   Specifies the text message for the tag created.<br>Example: Text
  * @param parameters
  *   Specifies the message parameters list.
  * @param localizedText
  *   Specifies the message ID and value.<br>Example: localizedText
  */
case class Message(
    code: Option[String] = None,
    text: Option[String] = None,
    parameters: Option[Seq[MessageParameter]] = None,
    localizedText: Option[String] = None
)

object Message {

  given Encoder[Message] = new Encoder.AsObject[Message] {
    final def encodeObject(o: Message): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"          -> o.code.asJson,
          "text"          -> o.text.asJson,
          "parameters"    -> o.parameters.asJson,
          "localizedText" -> o.localizedText.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Message] = (c: HCursor) => {
    for {
      code          <- c.downField("code").as[Option[String]]
      text          <- c.downField("text").as[Option[String]]
      parameters    <- c.downField("parameters").as[Option[Seq[MessageParameter]]]
      localizedText <- c.downField("localizedText").as[Option[String]]
    } yield Message(code, text, parameters, localizedText)
  }
}
