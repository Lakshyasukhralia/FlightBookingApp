package com.sukhralia.flightsearch.flight.model

data class AirlineModel(
    var origin : String = "",
    var destination : String = "",
    var departureTime : String = "",
    var arrivalTime : String = "",
    var provider : String = "",
    var fare : Int = 0,
    var airline : String = "",
    var type : String = ""
)