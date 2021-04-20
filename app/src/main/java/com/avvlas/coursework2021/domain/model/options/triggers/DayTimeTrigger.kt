package com.avvlas.coursework2021.domain.model.options.triggers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import com.avvlas.coursework2021.utils.broadcastreceivers.TriggerBroadcastReceiver
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class DayTimeTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_watch_24,
    override val title: String = "Day/Time Trigger",
    var hour: Int = -1,
    var minute: Int = -1,
    val days: ArrayList<Boolean> = arrayListOf()
) : Trigger(icon, title) {

    override fun schedule(context: Context, macro: Macro) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, TriggerBroadcastReceiver::class.java)
        intent.putExtra(TriggerBroadcastReceiver.MACRO, macro)
        intent.putExtra(TriggerBroadcastReceiver.TRIGGER_TYPE, "Day/Time Trigger")

        val alarmPendingIntent = PendingIntent.getBroadcast(context, macro.hashCode(), intent, 0)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        // if alarm time has already passed, increment day by 1
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent
        )
    }

    override fun cancel(context: Context, macro: Macro) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TriggerBroadcastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, macro.hashCode(), intent, 0)
        alarmManager.cancel(alarmPendingIntent)
    }

    override fun onClick(context: Context, macro: Macro) {
        TODO("Not yet implemented")
    }
}