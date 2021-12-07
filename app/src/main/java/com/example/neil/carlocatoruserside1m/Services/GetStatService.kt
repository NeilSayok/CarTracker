package com.example.neil.carlocatoruserside1m.Services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.Observer
import com.example.neil.carlocatoruserside1m.DefaultActivity

class GetStatService: Service () {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        




        return START_STICKY
    }


}