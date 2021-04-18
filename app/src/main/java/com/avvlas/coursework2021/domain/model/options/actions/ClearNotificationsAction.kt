package com.avvlas.coursework2021.domain.model.options.actions

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.utils.services.NotificationListenerServiceImpl
import com.avvlas.coursework2021.utils.services.NotificationListenerServiceImpl.LocalBinder
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
class ClearNotificationsAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_notifications_off_24,
    override val title: String = "Clear Notifications"
) : Action(icon, title) {

    private var context: Context? = null

    override fun execute(context: Context) {
        // TODO: not working
        this.context = context
        val intent = Intent(context, NotificationListenerServiceImpl::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun clearNotifications() {
        val activeNotifications = notificationListenerService?.activeNotifications
        Log.d("notifications", activeNotifications.toString())
        activeNotifications?.forEach {
            notificationListenerService?.cancelNotification(it.key)
        }
    }

    @IgnoredOnParcel
    private var notificationListenerService: NotificationListenerServiceImpl? = null

    @IgnoredOnParcel
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // cast the IBinder and get MyService instance
            val binder = service as LocalBinder
            notificationListenerService = binder.service
            clearNotifications()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            notificationListenerService = null
        }
    }
}