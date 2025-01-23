package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the information about delays.
  *
  * @param `type`
  *   Specifies the type of delay.
  * @param subType
  *   Specifies the subType of delay.
  * @param status
  *   Specifies the status of package indicating whether a package is arriving early or is on time or has been delayed.
  */
case class DelayDetail(
    `type`: Option[DelayDetail.Type] = None,
    subType: Option[DelayDetail.SubType] = None,
    status: Option[DelayDetail.Status] = None
)

object DelayDetail {
  enum SubType {
    case SNOW
    case TORNADO
    case `EARTHQUAKE etc`
  }
  object SubType {
    given Encoder[SubType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[SubType] = Decoder.decodeString.emapTry(s => scala.util.Try(SubType.valueOf(s)))
  }

  enum Type {
    case WEATHER
    case OPERATIONAL
    case LOCAL
    case GENERAL
    case CLEARANCE
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.emapTry(s => scala.util.Try(Type.valueOf(s)))
  }

  enum Status {
    case DELAYED
    case ON_TIME
    case EARLY
  }
  object Status {
    given Encoder[Status] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Status] = Decoder.decodeString.emapTry(s => scala.util.Try(Status.valueOf(s)))
  }
  given Encoder[DelayDetail] = new Encoder.AsObject[DelayDetail] {
    final def encodeObject(o: DelayDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "type"    -> o.`type`.asJson,
          "subType" -> o.subType.asJson,
          "status"  -> o.status.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DelayDetail] = (c: HCursor) => {
    for {
      `type`  <- c.downField("type").as[Option[Type]]
      subType <- c.downField("subType").as[Option[SubType]]
      status  <- c.downField("status").as[Option[Status]]
    } yield DelayDetail(`type`, subType, status)
  }
}
