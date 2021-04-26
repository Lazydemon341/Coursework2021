package com.avvlas.coursework2021.utils.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.avvlas.coursework2021.App
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.MainActivity


class AppForegroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // do your jobs here
        startForeground()
        return START_STICKY
    }

    private fun startForeground() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        startForeground(
            NOTIFICATION_ID, NotificationCompat.Builder(
                this,
                App.CHANNEL_ID
            )
                // TODO: set icon and title
                .setOngoing(true)
                //.setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build()
        )
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}