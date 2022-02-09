package com.ics342.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ForecastActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private val adapterData = listOf<DayForecast>(
        // Used this for hard coded
        DayForecast(
            1643665795,
            "Sunrise: 5:01am",
            "Sunset: 9:00pm",
            "Temp: 72°",
            "High: 80°",
            "Low: 65°"
        ),     // Jan 31, 2022
        DayForecast(
            1643752195,
            "Sunrise: 5:12am",
            "Sunset: 9:05pm",
            "Temp: 75°",
            "High: 88°",
            "Low: 55°"
        ),   // Feb 1
        DayForecast(
            1643838595,
            "Sunrise: 5:23am",
            "Sunset: 9:10pm",
            "Temp: 79°",
            "High: 80°",
            "Low: 45°"
        ),   // Feb 2
        DayForecast(
            1643924995,
            "Sunrise: 5:34am",
            "Sunset: 9:15pm",
            "Temp: 72°",
            "High: 89°",
            "Low: 55°"
        ),   // Feb 3
        DayForecast(
            1644011395,
            "Sunrise: 5:45am",
            "Sunset: 9:20pm",
            "Temp: 70°",
            "High: 88°",
            "Low: 69°"
        ),   // Feb 4
        DayForecast(
            1644099673,
            "Sunrise: 5:51am",
            "Sunset: 9:25pm",
            "Temp: 70°",
            "High: 100°",
            "Low: 65°"
        ),   // Feb 5
        DayForecast(
            1644186073,
            "Sunrise: 6:01am",
            "Sunset: 9:30pm",
            "Temp: 65°",
            "High: 89°",
            "Low: 65°"
        ),   // Feb 6
        DayForecast(
            1644272473,
            "Sunrise: 6:11am",
            "Sunset: 9:35pm",
            "Temp: 55°",
            "High: 80°",
            "Low: 54°"
        ),   // Feb 7
        DayForecast(
            1644358873,
            "Sunrise: 6:21am",
            "Sunset: 9:40pm",
            "Temp: 89°",
            "High: 90°",
            "Low: 55°"
        ),   // Feb 8
        DayForecast(
            1644445273,
            "Sunrise: 6:31am",
            "Sunset: 9:45pm",
            "Temp: 80°",
            "High: 80°",
            "Low: 69°"
        ),   // Feb 9
        DayForecast(
            1644531673,
            "Sunrise: 5:01am",
            "Sunset: 9:30pm",
            "Temp: 72°",
            "High: 90°",
            "Low: 65°"
        ),   // Feb 10
        DayForecast(
            1644618073,
            "Sunrise: 5:01am",
            "Sunset: 9:25pm",
            "Temp: 77°",
            "High: 80°",
            "Low: 56°"
        ),   // Feb 11
        DayForecast(
            1644704473,
            "Sunrise: 5:01am",
            "Sunset: 9:20pm",
            "Temp: 88°",
            "High: 90°",
            "Low: 70°"
        ),   // Feb 12
        DayForecast(
            1644790873,
            "Sunrise: 5:01am",
            "Sunset: 9:15pm",
            "Temp: 89°",
            "High: 99°",
            "Low: 68°"
        ),   // Feb 13
        DayForecast(
            1644877273,
            "Sunrise: 5:01am",
            "Sunset: 9:10pm",
            "Temp: 100°",
            "High: 111°",
            "Low: 59°"
        ),   // Feb 14
        DayForecast(
            1644963673,
            "Sunrise: 5:01am",
            "Sunset: 9:05pm",
            "Temp: 66°",
            "High: 80°",
            "Low: 50°"
        )   // Feb 15

        // THIS IS FOR NEXT ASSIGNMENT
        /*
            DayForecast(1643665795, 501, 2200, 70, )    // Jan 31, 2022
            DayForecast(1643752195, 502, 2201),   // Feb 1
            DayForecast(1643838595, 503, 2202),   // Feb 2
            DayForecast(1643924995, 504, 2203),   // Feb 3
            DayForecast(1644011395, 506, 2204),   // Feb 4
            Data(1644099673),   // Feb 5
            Data(1644186073),   // Feb 6
            Data(1644272473),   // Feb 7
            Data(1644358873),   // Feb 8
            Data(1644445273),   // Feb 9
            Data(1644531673),   // Feb 10
            Data(1644618073),   // Feb 11
            Data(1644704473),   // Feb 12
            Data(1644790873),   // Feb 13
            Data(1644877273),   // Feb 14
            Data(1644963673),   // Feb 15
             */
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(adapterData)

    }
}