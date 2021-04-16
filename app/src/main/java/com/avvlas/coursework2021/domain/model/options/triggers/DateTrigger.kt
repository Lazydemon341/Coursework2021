package com.avvlas.coursework2021.domain.model.options.triggers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import com.avvlas.coursework2021.utils.broadcastreceivers.TriggerBroadcastReceiver
import kotlinx.parcelize.Parcelize

@Parcelize
class DateTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_calendar_today_24,
    override val title: String = "Date Trigger",
    var timeInMillis: Long = -1
) : Trigger(icon, title) {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun schedule(macro: Macro, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, TriggerBroadcastReceiver::class.java)
        intent.putExtra(TriggerBroadcastReceiver.MACRO, macro)
        intent.putExtra(TriggerBroadcastReceiver.TRIGGER, this)
        intent.action = "DateTrigger"

        val alarmPendingIntent = PendingIntent.getBroadcast(context, macro.hashCode(), intent, PendingIntent.FLAG_ONE_SHOT)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            alarmPendingIntent
        )
    }

    override fun cancel(macro: Macro, context: Context) {
        TODO("Not yet implemented")
    }
}