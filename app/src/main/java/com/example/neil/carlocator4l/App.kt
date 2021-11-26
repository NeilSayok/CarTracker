package com.example.neil.carlocator4l

import android.app.Application
import android.os.Build
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent

class App : Application() {

    public companion object{
        public val CHANNEL_ID = "TestChannel"
        val channel_name = "TestChannel"
        val channel_description = "Notification to be creaed on test channel"
        val notificationId = 0
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel();
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channel_name
            val descriptionText = channel_description
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }


}