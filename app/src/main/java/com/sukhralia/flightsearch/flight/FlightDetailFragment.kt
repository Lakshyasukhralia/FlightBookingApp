package com.sukhralia.flightsearch.flight

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukhralia.flightsearch.R
import com.sukhralia.flightsearch.databinding.FragmentFlightDetailBinding
import com.sukhralia.flightsearch.flight.adapter.FlightAdapter
import com.sukhralia.flightsearch.flight.model.AirlineModel
import com.sukhralia.flightsearch.flight.network.FlightModel
import com.sukhralia.flightsearch.flight.util.AppUtils
import com.sukhralia.flightsearch.flight.viewmodel.FlightViewModel

class FlightDetailFragment : Fragment() {

    private val viewModel: FlightViewModel by lazy {
        ViewModelProviders.of(this).get(FlightViewModel::class.java)
    }

    private lateinit var airlineList: ArrayList<AirlineModel>
    private lateinit var airlineMap: Map<String, String>
    private lateinit var airportMap: Map<String, String>
    private lateinit var providerMap: Map<String, String>

    private lateinit var binding: FragmentFlightDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentFlightDetailBinding>(
            inflater,
            R.layout.fragment_flight_detail,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.flightViewModel = viewModel

        viewModel.flightResponse.observe(this, Observer { response ->
            convertToAirline(response)
        })

        return binding.root
    }

    private fun convertToAirline(flightModel: FlightModel) {

        val flightList = flightModel.flights

        airlineMap = flightModel.appendix.airlines
        airportMap = flightModel.appendix.airports
        providerMap = flightModel.appendix.providers

        if (flightList.isNotEmpty()) {

            airlineList = ArrayList()

            for (flight in flightList) {
                for (i in 0 until flight.fares.size) {
                    val model = AirlineModel()

                    model.origin = airportMap.getValue(flight.originCode)
                    model.destination = airportMap.getValue(flight.destinationCode)
                    model.provider = providerMap.getValue(flight.fares[i].providerId.toString())
                    model.fare = flight.fares[i].fare
                    model.airline = airlineMap.getValue(flight.airlineCode)
                    model.type = flight.`class`
                    model.departureTime = AppUtils.millisToDate(flight.departureTime)
                    model.arrivalTime = AppUtils.millisToDate(flight.arrivalTime)

                    airlineList.add(model)
                }
            }
        }

        handleUI()


    }

    private fun handleUI() {
        binding.flightList.layoutManager = LinearLayoutManager(
            context as MainActivity,
            LinearLayoutManager.VERTICAL, false
        )

        val adapter = FlightAdapter()
        adapter.mContext = context as MainActivity

        binding.flightList.adapter = adapter
        adapter.data = airlineList

        binding.fare.setOnClickListener {
            adapter.data = airlineList.sortedWith(compareBy { it.fare })
        }

        binding.departureTime.setOnClickListener {
            adapter.data = airlineList.sortedWith(compareBy { it.departureTime })
        }

        binding.arrivalTime.setOnClickListener {
            adapter.data = airlineList.sortedWith(compareBy { it.arrivalTime })
        }

    }


}