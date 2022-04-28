package com.ics342.weatherapp

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.*

class MyService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        getLocation()

        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location == null) {
                Toast.makeText(this, "Can Not Get Location.", Toast.LENGTH_SHORT).show()
                showLocationNotFoundDialog()
            } else {
                Toast.makeText(this, "Location Found.", Toast.LENGTH_SHORT).show()

                latitude = location?.latitude
                longitude = location?.longitude

                requestNewLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun requestNewLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = (30L * 60L * 1000L)  // 30 minutes
        locationRequest.fastestInterval = (30L * 60L * 1000L)   // 30 minutes
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val locationProvider = LocationServices.getFusedLocationProviderClient(this)
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.forEach {

                }
            }
        }
        locationProvider.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun showLocationNotFoundDialog() {
        AlertDialog.Builder(this)
            .setMessage(R.string.location_not_found)
            .setNeutralButton(R.string.location_not_found_button) { _, _ ->
            }.create().show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val intent = Intent(this, SearchFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Icon image
        val bitmap: Bitmap =
            BitmapFactory.decodeResource(applicationContext.resources, R.drawable.sun)
        val bitmapLargeIcon: Bitmap =
            BitmapFactory.decodeResource(applicationContext.resources, R.drawable.sun)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.sun)
            .setContentTitle("80°")
            .setContentText("Minneapolis")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(ONGOING_NOTIFICATION_ID, builder.build())
        }

        startForeground(ONGOING_NOTIFICATION_ID, builder.build())
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "notification_channel"
        const val ONGOING_NOTIFICATION_ID = 1
    }
}