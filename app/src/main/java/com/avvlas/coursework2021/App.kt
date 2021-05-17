package com.avvlas.coursework2021

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
        startService(Intent(this, AppForegroundService::class.java))
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(
                NotificationChannel(
                    FOREGROUND_SERVICE_CHANNEL_ID,
                    getString(R.string.background_work_notification_name),
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    setShowBadge(false)
                }
            )
            manager.createNotificationChannel(
                NotificationChannel(
                    MACROS_NOTIFICATIONS_CHANNEL_ID,
                    getString(R.string.macros_notification_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
    }

    companion object {
        const val MACROS_NOTIFICATIONS_CHANNEL_ID: String = "MACROS_NOTIFICATION_CHANNEL"
        const val FOREGROUND_SERVICE_CHANNEL_ID: String = "FOREGROUND_SERVICE_NOTIFICATION_CHANNEL"
    }
}