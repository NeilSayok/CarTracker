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
import android.os.Build
import android.os.IBinder
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
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class LocationService: Service() {

    private lateinit var api: RetrofitAPI
    private lateinit var sp: SharedPreferences

    private lateinit var notificationIntent: Intent
    private lateinit var pendingIntent: PendingIntent
    private lateinit var notification: Notification


    var locationManager: LocationManager? = null
    private lateinit var locationListener: LocationListener
    
    private lateinit var email: String
    private lateinit var broadCastIntent : Intent

    val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy/HH:mm:ss", Locale.US)





    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        sp = getSharedPreferences("LOGIN", MODE_PRIVATE)
        api = RetrofitBuilder().retrofit.create(RetrofitAPI::class.java)
        broadCastIntent = Intent("location_update")

        email = sp.getString("email","").toString()

        Log.d("Email", email.toString())

        updateLogStat(1)

        notificationIntent = Intent(this, DefaultActivity::class.java)
        pendingIntent = PendingIntent.getActivity(this,0, notificationIntent, 0)

        notification = NotificationCompat.Builder(this, App.CHANNEL_ID)
            .setContentTitle("Car Locator")
            .setContentText("Tracking Your Car : " + sp.getString("reg_id", ""))
            .setSmallIcon(R.drawable.ic_location_on_black_24dp)
            .setContentIntent(pendingIntent)
            .build()


        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager

        locationListener = LocationListener {
            updateLocation(it.latitude,it.longitude,it.time)
            broadCastIntent.putExtra("latitude", it.latitude)
            broadCastIntent.putExtra("longitude", it.longitude)
            broadCastIntent.putExtra("time", getFormattedDate(it.time))
            sendBroadcast(broadCastIntent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            &&
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            onDestroy()
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O
            &&
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){

            onDestroy()
        }else{
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0f, locationListener)
            startForeground(1, notification)
            return START_STICKY
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        sp.edit().putBoolean("serviceStat", false).apply()
        if (locationManager != null) {
            locationManager!!.removeUpdates(locationListener)
        }
        updateLogStat(0)
    }

    private fun updateLogStat(log_stat: Int){
        val call = api.write_log_stat(email,log_stat)

        call.enqueue(object: Callback<SimpleResponseData> {
            override fun onResponse(
                call: Call<SimpleResponseData>,
                response: Response<SimpleResponseData>
            ) {
                Log.d("write_log_stat-Success",response.body().toString())
            }

            override fun onFailure(call: Call<SimpleResponseData>, t: Throwable) {
                Log.d("write_log_stat-Error",t.toString())
            }

        })
    }

    private fun updateLocation(latitude:Double, longitude:Double,time: Long){
        val call = api.update_location(
            email,
            latitude ,
            longitude ,
            System.currentTimeMillis()
        )

        call.enqueue(object: Callback<SimpleResponseData>{
            override fun onResponse(
                call: Call<SimpleResponseData>,
                response: Response<SimpleResponseData>
            ) {
                Log.d("updateLocation-Success",response.body().toString())
            }

            override fun onFailure(call: Call<SimpleResponseData>, t: Throwable) {
                Log.d("updateLocation-Error",t.toString())
            }

        })
    }

    private fun getFormattedDate(time:Long):String = dateFormat.format(Date(time))
}