package com.sukhralia.flightsearch.flight.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "dd-MM-yy, hh:mm a"

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
}