package com.sukhralia.flightsearch.flight.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val DATE_FORMAT = "dd-MM-yy,\nhh:mm a"

object AppUtils {

    fun millisToDate(millis: Long) : String {
        return SimpleDateFormat(DATE_FORMAT, Locale.US).format(Date(millis))
    }

    fun stringToDate(date : String) : Date {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)

        try {
            return dateFormat.parse(date)
        } catch (e : ParseException) {
            e.printStackTrace()
        }
        return Date()
    }

    fun calculateDuration(start : String, end : String): String {

        val timeDiffInMillis = stringToDate(end).time - stringToDate(start).time

        return String.format(
            "%d Hrs, %d Mins",
            TimeUnit.MILLISECONDS.toSeconds(timeDiffInMillis) / (60 * 60),
            TimeUnit.MILLISECONDS.toSeconds(timeDiffInMillis) % (60 * 60)
        )
    }
}