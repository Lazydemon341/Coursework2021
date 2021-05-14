package com.avvlas.coursework2021.model.options.triggers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.utils.broadcastreceivers.AlarmReceiver
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class RepeatingTimeTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_watch_24,
    @StringRes override val title: Int = R.string.day_time_trigger_title,
    var hour: Int = -1,
    var minute: Int = -1,
    val days: ArrayList<Boolean> = arrayListOf()
) : Trigger(icon, title) {

    override fun schedule(appContext: Context, macro: Macro) {
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(appContext, AlarmReceiver::class.java)
        intent.putExtra(AlarmReceiver.MACRO, macro)
        intent.action = this.javaClass.simpleName

        val alarmPendingIntent = PendingIntent.getBroadcast(appContext, macro.hashCode(), intent, 0)

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

    // TODO: dodelat'

    override fun cancel(context: Context, macro: Macro) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, macro.hashCode(), intent, 0)
        alarmManager.cancel(alarmPendingIntent)
    }

    override fun onClick(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            dateTimePicker { dialog, datetime ->

            }
        }
    }
}