package com.avvlas.coursework2021.utils.services

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification


class NotificationListenerServiceImpl : NotificationListenerService() {
    private val binder = LocalBinder()
    val notifications: ArrayList<StatusBarNotification> = arrayListOf()

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        notifications.add(sbn)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        notifications.remove(sbn)
    }

    inner class LocalBinder : Binder() {
        val service: NotificationListenerServiceImpl
            get() = this@NotificationListenerServiceImpl
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}