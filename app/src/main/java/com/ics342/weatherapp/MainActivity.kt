package com.ics342.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint

/**
 * This Main Activity Class is operating as a View.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val apikey = "d68b2cd5f58a3e8473f5921cb7afb26e"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
