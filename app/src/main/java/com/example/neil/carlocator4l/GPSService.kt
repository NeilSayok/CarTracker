package com.example.neil.carlocator4l

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
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
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
    private lateinit var writeStatURL:kotlin.String

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        writeStatURL = getString(R.string.write_log_url)
        sp = getSharedPreferences("LOGIN", MODE_PRIVATE)

        val sr: StringRequest = object : StringRequest(
            Method.POST, writeStatURL,
            Response.Listener { },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = sp.getString("email", "")!!
                params["stat"] = "1"
                return params
            }
        }


        VolleySingleton.getmInstance(applicationContext).addToRequestQue(sr)


        notificationIntent = Intent(this, MainActivity::class.java)
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

                val stringRequest: StringRequest = object : StringRequest(
                    Method.POST,
                    "https://car-locator-javalab-proj.000webhostapp.com/updatelocation.php",
                    Response.Listener { },
                    Response.ErrorListener { }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String>? {
                        val params: MutableMap<String, String> = HashMap()
                        params["email"] = sp.getString("email", "")!!
                        params["lat"] = java.lang.Double.toString(location.latitude)
                        params["time"] = hms
                        params["longi"] = java.lang.Double.toString(location.longitude)
                        return params
                    }
                }

                VolleySingleton.getmInstance(applicationContext).addToRequestQue(stringRequest)

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
            locationManager!!.removeUpdates(listener!!)
        }
        Log.d("Service", "stopped")
        sp.edit().putBoolean("serviceStat", false).apply()

        val sr: StringRequest = object : StringRequest(
            Method.POST, writeStatURL,
            Response.Listener { },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = sp!!.getString("email", "")!!
                params["stat"] = "0"
                return params
            }
        }
        VolleySingleton.getmInstance(applicationContext).addToRequestQue(sr)
    }
    
    
    
}