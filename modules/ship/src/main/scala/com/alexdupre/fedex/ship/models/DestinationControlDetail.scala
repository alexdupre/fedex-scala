package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to specify the appropriate destination control statement type(s). Also make sure to specify destination country and end
  * user.
  *
  * @param statementTypes
  *   Specify appropriate destination control statement type(s), Also make sure to specify destination country and end user.
  * @param endUser
  *   Specify End User name. Its is required if StatementTypes is entered as DEPARTMENT_OF_STATE. <br>Example: John Wick
  * @param destinationCountries
  *   Specify DCS shipment destination country. You may enter up to four country codes in this element. Up to 11 alphanumeric characters are
  *   allowed.<br>Example: US<br><a onclick='loadDocReference("countrycodes")'>click here to see Country codes</a>
  */
case class DestinationControlDetail(
    statementTypes: DestinationControlDetail.StatementTypes,
    endUser: Option[String] = None,
    destinationCountries: Option[Seq[String]] = None
)

object DestinationControlDetail {
  enum StatementTypes {
    case DEPARTMENT_OF_COMMERCE
    case DEPARTMENT_OF_STATE
    case UNKNOWN_DEFAULT
  }
  object StatementTypes {
    given Encoder[StatementTypes] = Encoder.encodeString.contramap(_.toString)
    given Decoder[StatementTypes] =
      Decoder.decodeString.map(s => scala.util.Try(StatementTypes.valueOf(s)).getOrElse(StatementTypes.UNKNOWN_DEFAULT))
  }
  given Encoder[DestinationControlDetail] = new Encoder.AsObject[DestinationControlDetail] {
    final def encodeObject(o: DestinationControlDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "statementTypes"       -> o.statementTypes.asJson,
          "endUser"              -> o.endUser.asJson,
          "destinationCountries" -> o.destinationCountries.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DestinationControlDetail] = (c: HCursor) => {
    for {
      statementTypes       <- c.downField("statementTypes").as[StatementTypes]
      endUser              <- c.downField("endUser").as[Option[String]]
      destinationCountries <- c.downField("destinationCountries").as[Option[Seq[String]]]
    } yield DestinationControlDetail(statementTypes, endUser, destinationCountries)
  }
}
