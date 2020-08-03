package com.sukhralia.flightsearch.flight.network

import com.squareup.moshi.Json

data class FlightModel(
    @Json(name = "appendix") val appendix: Appendix,
    @Json(name = "flights") val flights: List<Flight>
)

data class Appendix(
    @Json(name = "airlines") val airlines: Map<String,String>,
    @Json(name = "airports") val airports: Map<String,String>,
    @Json(name = "providers") val providers: Map<String,String>
)

data class Flight(
    @Json(name = "airlineCode") val airlineCode: String,
    @Json(name = "arrivalTime") val arrivalTime: Long,
    @Json(name = "class") val `class`: String,
    @Json(name = "departureTime") val departureTime: Long,
    @Json(name = "destinationCode") val destinationCode: String,
    @Json(name = "fares") val fares: List<Fare>,
    @Json(name = "originCode") val originCode: String
)

data class Fare(
    @Json(name = "fare") val fare: Int,
    @Json(name = "providerId") val providerId: Int
)