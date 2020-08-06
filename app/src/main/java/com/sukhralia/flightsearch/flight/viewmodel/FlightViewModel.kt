package com.sukhralia.flightsearch.flight.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukhralia.flightsearch.BuildConfig
import com.sukhralia.flightsearch.flight.constants.AppURLConstants
import com.sukhralia.flightsearch.flight.network.FlightApi
import com.sukhralia.flightsearch.flight.network.FlightModel
import kotlinx.coroutines.*

class FlightViewModel : ViewModel(){

    //Declared live data to observe immediate changes
    private val _response = MutableLiveData<String>()
    private val _flightResponse = MutableLiveData<FlightModel>()

    val response : LiveData<String>
        get() = _response

    val flightResponse : LiveData<FlightModel>
        get() = _flightResponse

    //Declare coroutine job and scope for api calls
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getFlights()
    }

    private fun getFlights(){

        coroutineScope.launch {
            getResult()
        }

    }

    private suspend fun getResult(){
        var getFlightsDeferred = FlightApi.retrofitService.getFlightsAsync(AppURLConstants.FLIGHT_DATA_URL)

        try {
            //Await response from server and then assign to value
            var flightResult = getFlightsDeferred.await()
            _response.value = "${flightResult.flights.size} flights retrieved"
            _flightResponse.value = flightResult
        }catch (t : Throwable){
            _response.value = "Failure = ${t.message}"
        }
    }

    //Clear all finished jobs
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}