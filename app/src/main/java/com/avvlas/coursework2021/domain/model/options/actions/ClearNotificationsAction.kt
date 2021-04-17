package com.avvlas.coursework2021.domain.model.options.actions

import android.app.NotificationManager
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.avvlas.coursework2021.R
import kotlinx.parcelize.Parcelize

@Parcelize
class ClearNotificationsAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_notifications_off_24,
    override val title: String = "Clear "
) : Action(icon, title) {

    override fun execute(context: Context) {
        val notificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }
}