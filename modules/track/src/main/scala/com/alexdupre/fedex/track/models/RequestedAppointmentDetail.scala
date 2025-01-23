package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Field which specifies the details of the requested appointment.
  *
  * @param date
  *   Field which holds the requested appointment date.<br>Format: YYYY-MM-DD<br> example: '2019-05-07'
  * @param window
  *   Array of different appointment time windows available on the date specified such as, Morning, afternoon, mid-day.
  */
case class RequestedAppointmentDetail(
    date: Option[String] = None,
    window: Option[Seq[TimeWindow]] = None
)

object RequestedAppointmentDetail {

  given Encoder[RequestedAppointmentDetail] = new Encoder.AsObject[RequestedAppointmentDetail] {
    final def encodeObject(o: RequestedAppointmentDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "date"   -> o.date.asJson,
          "window" -> o.window.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RequestedAppointmentDetail] = (c: HCursor) => {
    for {
      date   <- c.downField("date").as[Option[String]]
      window <- c.downField("window").as[Option[Seq[TimeWindow]]]
    } yield RequestedAppointmentDetail(date, window)
  }
}
