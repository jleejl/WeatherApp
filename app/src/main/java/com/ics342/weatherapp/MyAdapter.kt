package com.ics342.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MyAdapter(private val data: List<DayForecast>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    @SuppressLint("NewApi")
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val formatter1 = DateTimeFormatter.ofPattern("MMM dd")
        private val formatter2 = DateTimeFormatter.ofPattern("h:mma")
        private val dateView: TextView = view.findViewById(R.id.date)
        private val sunriseView: TextView = view.findViewById(R.id.sunrise)
        private val sunsetView: TextView = view.findViewById(R.id.sunset)
        private val tempView: TextView = view.findViewById(R.id.temp)
        private val highView: TextView = view.findViewById(R.id.high)
        private val lowView: TextView = view.findViewById(R.id.low)

        fun bind(data: DayForecast) {
            val instant = Instant.ofEpochSecond(data.date)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            dateView.text = formatter1.format(dateTime)
        }

        fun bindSunrise(data: DayForecast) {
            // THIS IS FOR NEXT ASSIGNMENT
            //val instant = Instant.ofEpochSecond(data.sunrise)
            //val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            //sunriseView.text = formatter2.format(dateTime)

            sunriseView.text = data.sunrise // Used this for hard coded
        }

        fun bindSunset(data: DayForecast) {
            // THIS IS FOR NEXT ASSIGNMENT
            //val instant = Instant.ofEpochSecond(data.sunset)
            //val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            //sunsetView.text = formatter2.format(dateTime)

            sunsetView.text = data.sunset   // Used this for hard coded
        }

        fun bindTemp(data: DayForecast) {
            // THIS IS FOR NEXT ASSIGNMENT
            //val instant = Instant.ofEpochSecond(data.temp)
            tempView.text = data.temp    // Used this for hard coded
        }

        fun bindHigh(data: DayForecast) {
            // THIS IS FOR NEXT ASSIGNMENT
            //val instant = Instant.ofEpochSecond(data.temp)
            highView.text = data.high    // Used this for hard coded
        }

        fun bindLow(data: DayForecast) {
            // THIS IS FOR NEXT ASSIGNMENT
            //val instant = Instant.ofEpochSecond(data.temp)
            lowView.text = data.low    // Used this for hard coded
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_date, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.bindSunrise(data[position])
        holder.bindSunset(data[position])
        holder.bindTemp(data[position])
        holder.bindHigh(data[position])
        holder.bindLow(data[position])
    }

    override fun getItemCount() = data.size
}