package com.avvlas.coursework2021

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import com.avvlas.coursework2021.utils.services.AppForegroundService
import dagger.hilt.android.HiltAndroidApp


// TODO:

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
        startService(Intent(this, AppForegroundService::class.java))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "AutoDroid notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        const val CHANNEL_ID: String = "APP_SERVICE_CHANNEL"
    }
}