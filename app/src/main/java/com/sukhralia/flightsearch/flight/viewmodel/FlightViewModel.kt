package com.sukhralia.flightsearch.flight.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukhralia.flightsearch.flight.network.FlightApi
import com.sukhralia.flightsearch.flight.network.FlightModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class FlightViewModel : ViewModel(){

    private val _response = MutableLiveData<String>()
    private val _flightResponse = MutableLiveData<FlightModel>()

    val response : LiveData<String>
        get() = _response

    val flightResponse : LiveData<FlightModel>
        get() = _flightResponse

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties(){

        coroutineScope.launch {
            getResult()
        }

    }

    private suspend fun getResult(){
        var getPropertiesDeferred = FlightApi.retrofitService.getProperties()

        try {
            var flightResult = getPropertiesDeferred.await()
            _response.value = "Flights retrieved = ${flightResult.flights.size}"
            _flightResponse.value = flightResult
        }catch (t : Throwable){
            _response.value = "Failure = ${t.message}"
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}