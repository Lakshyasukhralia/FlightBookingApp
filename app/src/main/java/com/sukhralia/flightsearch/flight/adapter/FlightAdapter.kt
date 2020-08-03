package com.sukhralia.flightsearch.flight.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sukhralia.flightsearch.R
import com.sukhralia.flightsearch.flight.FlightDetailFragment
import com.sukhralia.flightsearch.flight.model.AirlineModel
import kotlinx.android.synthetic.main.flight_item.view.*

class FlightAdapter() : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

    lateinit var  mContext: Context
    var data = listOf<AirlineModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlightAdapter.FlightViewHolder {
        return FlightViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.flight_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {

        when (holder) {
            is FlightViewHolder -> {
                holder.bind(data[position])
            }

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitList(flightList : List<AirlineModel>) {
        data = flightList
    }


    inner class FlightViewHolder
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val provider: TextView = itemView.provider
        private val classType: TextView = itemView.class_type
        private val airline: TextView = itemView.airline
        private val origin: TextView = itemView.origin
        private val destination: TextView = itemView.destination
        private val dTime: TextView = itemView.d_time
        private val aTime: TextView = itemView.a_time
        private val fare: TextView = itemView.fare

        fun bind(flight : AirlineModel) {

            origin.text = flight.origin
            destination.text = flight.destination
            fare.text = flight.fare.toString()

        }


    }


}