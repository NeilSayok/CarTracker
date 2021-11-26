package com.example.neil.carlocator4l.Services

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.neil.carlocator4l.App
import com.example.neil.carlocator4l.DefaultActivity
import com.example.neil.carlocator4l.R

import neilsayok.github.carlocatorapi.API.Data.SimpleResponseData
import neilsayok.github.carlocatorapi.API.Retrofit.RetrofitAPI
import neilsayok.github.carlocatorapi.API.Retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class GPSService : Service(){

    private lateinit var listener: LocationListener
    var locationManager: LocationManager? = null
    private lateinit var sp: SharedPreferences
    private lateinit var notificationIntent: Intent
    private lateinit var pendingIntent: PendingIntent
    private lateinit var notification: Notification
    private lateinit var hms: String

    private lateinit var api: RetrofitAPI



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("Serice","Started.")
        sp = getSharedPreferences("LOGIN", MODE_PRIVATE)

        api = RetrofitBuilder().retrofit.create(RetrofitAPI::class.java)




        val call = api.write_log_stat(
            sp.getString("email", ""),
            1
        )

        //call.execute()
        call.enqueue(object : Callback<SimpleResponseData>{
            override fun onResponse(
                call: Call<SimpleResponseData>,
                response: retrofit2.Response<SimpleResponseData>
            ) {}
            override fun onFailure(call: Call<SimpleResponseData>, t: Throwable) {}})





        notificationIntent = Intent(this, DefaultActivity::class.java)
        pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        notification = NotificationCompat.Builder(this, App.CHANNEL_ID)
            .setContentTitle("Car Locator")
            .setContentText("Tracking Your Car : " + sp.getString("reg_id", ""))
            .setSmallIcon(R.drawable.ic_location_on_black_24dp)
            .setContentIntent(pendingIntent)
            .build()

        listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                //This will be called when location is changed
                val date = Date(location.time)
                val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy/HH:mm:ss", Locale.US)
                hms = dateFormat.format(date)
                val i = Intent("location_update")
                i.putExtra("latitude", location.latitude)
                i.putExtra("longitude", location.longitude)
                i.putExtra("time", hms)

                //TODO Change to Retrofit
                val call = api.update_location(
                    sp.getString("email", ""),
                    location.latitude,
                    location.longitude,
                    location.time
                    )

//                call.execute()
                call.enqueue(object : Callback<SimpleResponseData>{
                    override fun onResponse(
                        call: Call<SimpleResponseData>,
                        response: retrofit2.Response<SimpleResponseData>
                    ) {Log.d("Location Update",response.body().toString())}
                    override fun onFailure(call: Call<SimpleResponseData>, t: Throwable) {}})

                Log.d(
                    "lat/long/time", java.lang.Double.toString(location.latitude) +
                            "/" + java.lang.Double.toString(location.longitude) + "/" +
                            hms
                )
                sendBroadcast(i)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {
                val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
            }
        }

        locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return START_STICKY
        }

        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0f, listener)

        startForeground(1, notification)
        return START_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()
        if (locationManager != null) {
            locationManager!!.removeUpdates(listener)
        }
        Log.d("Service", "stopped")
        sp.edit().putBoolean("serviceStat", false).apply()

        val call = api.write_log_stat(
            sp.getString("email", ""),
            0
        )

        //call.execute()
        call.enqueue(object : Callback<SimpleResponseData>{
            override fun onResponse(
                call: Call<SimpleResponseData>,
                response: retrofit2.Response<SimpleResponseData>
            ) {}
            override fun onFailure(call: Call<SimpleResponseData>, t: Throwable) {}})
    }
    
    
    
}