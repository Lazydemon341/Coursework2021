package com.avvlas.coursework2021

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
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
        val pendingIntent = Intent(this, MainActivity::class.java).let {
            PendingIntent.getActivity(this, 0, it, 0)
        }

        val notification = NotificationCompat.Builder(this, App.FOREGROUND_SERVICE_CHANNEL_ID)
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setShowWhen(false)
            .setContentText(getString(R.string.autodroid_is_running))
            .setContentTitle(getString(R.string.app_name))
            .setContentIntent(pendingIntent)
            .build()

        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }

    companion object {
        private const val ONGOING_NOTIFICATION_ID = 1
    }
}