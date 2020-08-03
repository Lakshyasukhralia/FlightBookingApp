package com.sukhralia.flightsearch.flight

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private var isFareLow = false
    private var isDeptLow = false
    private var isArrLow = false

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

        viewModel.response.observe(this, Observer { response ->
            Toast.makeText(context as MainActivity,response,Toast.LENGTH_SHORT).show()
        })

        binding.progressBar.visibility = View.VISIBLE
        viewModel.flightResponse.observe(this, Observer { response ->
            convertToAirline(response)
            binding.progressBar.visibility = View.GONE
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
            when(isFareLow){
                true -> {
                    isFareLow = false
                    Toast.makeText(context as MainActivity,"Fare : Low",Toast.LENGTH_SHORT).show()
                    adapter.data = airlineList.sortedWith(compareBy { it.fare })
                    binding.fare.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_arrow, 0)}
                false -> {
                    isFareLow = true
                    Toast.makeText(context as MainActivity,"Fare : High",Toast.LENGTH_SHORT).show()
                    adapter.data = airlineList.sortedWith(compareByDescending { it.fare })
                    binding.fare.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0)}
            }
        }

        binding.departureTime.setOnClickListener {

            when(isDeptLow){
                true -> {
                    isDeptLow = false
                    Toast.makeText(context as MainActivity,"Departure : Early",Toast.LENGTH_SHORT).show()
                    adapter.data = airlineList.sortedWith(compareBy { it.departureTime })
                    binding.departureTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_arrow, 0)}
                false -> {
                    isDeptLow = true
                    Toast.makeText(context as MainActivity,"Departure : Late",Toast.LENGTH_SHORT).show()
                    adapter.data = airlineList.sortedWith(compareByDescending { it.departureTime })
                    binding.departureTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0)}
            }

        }

        binding.arrivalTime.setOnClickListener {

            when(isArrLow){
                true -> {
                    isArrLow = false
                    Toast.makeText(context as MainActivity,"Arrival : Early",Toast.LENGTH_SHORT).show()
                    adapter.data = airlineList.sortedWith(compareBy { it.arrivalTime })
                    binding.arrivalTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_arrow, 0)}
                false -> {
                    isArrLow = true
                    Toast.makeText(context as MainActivity,"Arrival : Late",Toast.LENGTH_SHORT).show()
                    adapter.data = airlineList.sortedWith(compareByDescending { it.arrivalTime })
                    binding.arrivalTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0)}
            }
        }

    }


}