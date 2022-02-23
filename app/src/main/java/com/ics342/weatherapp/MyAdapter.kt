package com.ics342.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MyAdapter(private val data: List<DayForecast>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    @SuppressLint("NewApi")
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val formatter1 =
            DateTimeFormatter.ofPattern("MMM dd")  // Display date example, Jan 31
        private val formatter2 = DateTimeFormatter.ofPattern("h")   // Display hours example, 7
        private val formatter3 = DateTimeFormatter.ofPattern("mm")   // Display minutes example, 03

        // Data
        private var forecastIcon: ImageView = view.findViewById(R.id.condition_icon)
        private val dateView: TextView = view.findViewById(R.id.date)
        private val sunriseView: TextView = view.findViewById(R.id.sunrise)
        private val sunsetView: TextView = view.findViewById(R.id.sunset)
        private val tempView: TextView = view.findViewById(R.id.temp)
        private val maxView: TextView = view.findViewById(R.id.max)
        private val minView: TextView = view.findViewById(R.id.min)

        fun bindIcon(data: DayForecast) {
            val iconName = data.weather.firstOrNull()?.icon
            val iconUrl =
                "https://openweathermap.org/img/wn/${iconName}@2x.png"    // Has to be https

            Glide.with(this.forecastIcon)   // Can pass in a view
                .load(iconUrl)
                .into(forecastIcon)
        }

        fun bindDate(data: DayForecast) {
            val instant = Instant.ofEpochSecond(data.date)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            dateView.text = formatter1.format(dateTime)
        }

        fun bindSunrise(data: DayForecast) {
            val instant = Instant.ofEpochSecond(data.sunrise)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val hours = formatter2.format(dateTime).toInt()  // h
            val min = formatter3.format(dateTime).toInt()  // mm

            sunriseView.text = sunriseView.context.getString(R.string.sunrise, hours, min)
        }

        fun bindSunset(data: DayForecast) {
            val instant = Instant.ofEpochSecond(data.sunset)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val hours = formatter2.format(dateTime).toInt()   // h
            val min = formatter3.format(dateTime).toInt()  // mm

            sunsetView.text = sunsetView.context.getString(R.string.sunset, hours, min)
        }

        // Bind for day temperature, Temp
        fun bindTempDay(data: DayForecast) {
            tempView.text =
                tempView.context.getString(R.string.temp, data.temp.day.toInt())
        }

        // Bind for max temperature, Max/High
        fun bindTempMax(data: DayForecast) {
            maxView.text = maxView.context.getString(R.string.max, data.temp.max.toInt())
        }

        // Bind for min temperature, Min/Low
        fun bindTempMin(data: DayForecast) {
            minView.text = minView.context.getString(R.string.min, data.temp.min.toInt())
        }
    }

    /**
     * This is an Important Method for RecyclerView, called onCreateViewHolder.
     *
     * This inflates instance of row_date.xml, This means it reuses the view
     * over and over.
     *
     * Called by RecyclerView when it needs a new view holder of the specified type,
     * second parameter is type
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_date, parent, false)

        return ViewHolder(view)
    }

    /**
     * This is an Important Method for RecyclerView, called onBindViewHolder.
     *
     * Called by RecyclerView to display data at position,
     * second parameter is the position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindDate(data[position])
        holder.bindSunrise(data[position])
        holder.bindSunset(data[position])
        holder.bindTempDay(data[position])
        holder.bindTempMax(data[position])
        holder.bindTempMin(data[position])
        holder.bindIcon(data[position])
    }

    /**
     * This is an Important Method for RecyclerView, called getItemCount.
     *
     * Returns the total number items that can be displayed
     */
    override fun getItemCount() = data.size
}