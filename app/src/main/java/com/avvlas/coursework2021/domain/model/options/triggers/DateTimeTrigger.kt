package com.avvlas.coursework2021.domain.model.options.triggers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import com.avvlas.coursework2021.utils.Parcelables.toByteArray
import com.avvlas.coursework2021.utils.broadcastreceivers.TriggerBroadcastReceiver
import kotlinx.parcelize.Parcelize

@Parcelize
class DateTimeTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_calendar_today_24,
    override val title: String = "Date and Time",
    var timeInMillis: Long = -1
) : Trigger(icon, title) {

    override fun schedule(macro: Macro, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, TriggerBroadcastReceiver::class.java)
        val bundle = Bundle().apply {
            putByteArray(TriggerBroadcastReceiver.MACRO, macro.toByteArray())
            putString(TriggerBroadcastReceiver.TRIGGER_TYPE, TriggerBroadcastReceiver.DATETIME_TRIGGER)
        }
        intent.putExtras(bundle)
        intent.action = "DateTrigger"

        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            macro.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            alarmPendingIntent
        )
    }

    override fun cancel(macro: Macro, context: Context) {
        TODO("Not yet implemented")
    }
}