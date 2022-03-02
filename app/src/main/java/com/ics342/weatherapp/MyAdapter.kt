package com.ics342.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ics342.weatherapp.databinding.RowDateBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MyAdapter(private val data: List<DayForecast>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    @SuppressLint("NewApi")
    class ViewHolder(private val binding: RowDateBinding) : RecyclerView.ViewHolder(binding.root) {
        private val formatter1 =
            DateTimeFormatter.ofPattern("MMM dd")  // Display date example, Jan 31
        private val formatter2 = DateTimeFormatter.ofPattern("h")   // Display hours example, 7
        private val formatter3 = DateTimeFormatter.ofPattern("mm")   // Display minutes example, 03

        fun bind(data: DayForecast) {
            // Icon
            val iconName = data.weather.firstOrNull()?.icon
            val iconUrl =
                "https://openweathermap.org/img/wn/${iconName}@2x.png"  // Has to be https

            Glide.with(this.binding.conditionIcon)  // Can pass in a view
                .load(iconUrl)
                .into(binding.conditionIcon)
            // Date
            val dateInstant = Instant.ofEpochSecond(data.date)
            val dateTime = LocalDateTime.ofInstant(dateInstant, ZoneId.systemDefault())
            // Sunrise
            val sunriseInstant = Instant.ofEpochSecond(data.sunrise)
            val sunriseTime = LocalDateTime.ofInstant(sunriseInstant, ZoneId.systemDefault())
            val sunriseHours = formatter2.format(sunriseTime)   // h
            val sunriseMin = formatter3.format(sunriseTime) // mm
            // Sunset
            val sunsetInstant = Instant.ofEpochSecond(data.sunset)
            val sunsetTime = LocalDateTime.ofInstant(sunsetInstant, ZoneId.systemDefault())
            val sunsetHours = formatter2.format(sunsetTime) // h
            val sunsetMin = formatter3.format(sunsetTime)   // mm

            binding.date.text = formatter1.format(dateTime)
            binding.sunrise.text =
                binding.sunrise.context.getString(R.string.sunrise, sunriseHours, sunriseMin)
            binding.sunset.text =
                binding.sunset.context.getString(R.string.sunset, sunsetHours, sunsetMin)
            binding.temp.text =
                binding.temp.context.getString(R.string.temp, data.temp.day.toInt())
            binding.max.text = binding.max.context.getString(R.string.max, data.temp.max.toInt())
            binding.min.text = binding.min.context.getString(R.string.min, data.temp.min.toInt())
        }
    }

    /**
     * Important Method for RecyclerView, called onCreateViewHolder.
     * Inflates instance of row_date.xml for reusing the view.
     * Called by RecyclerView when it needs a new view holder of the specified type,
     * second parameter is type
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // This is now using the View Binding, RowDateBinding
        val view = RowDateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return ViewHolder(view)
    }

    /**
     * Important Method for RecyclerView, called onBindViewHolder.
     * Called by RecyclerView to display data at position, second parameter
     * is the position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    /**
     * Important Method for RecyclerView, called getItemCount.
     * Returns the total number items that can be displayed.
     */
    override fun getItemCount() = data.size
}