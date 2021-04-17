package com.avvlas.coursework2021.domain.model.options.actions

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationManagerCompat
import com.avvlas.coursework2021.R
import kotlinx.parcelize.Parcelize

@Parcelize
class ClearNotificationsAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_notifications_off_24,
    override val title: String = "Clear Notifications"
) : Action(icon, title) {

    override fun execute(context: Context) {
        // TODO: not working
        NotificationManagerCompat.from(context).cancelAll()
    }
}