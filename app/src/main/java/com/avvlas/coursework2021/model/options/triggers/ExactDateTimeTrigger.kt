package com.avvlas.coursework2021.model.options.triggers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.utils.Parcelables.toByteArray
import com.avvlas.coursework2021.utils.broadcastreceivers.AlarmReceiver
import kotlinx.parcelize.Parcelize

@Parcelize
class ExactDateTimeTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_calendar_today_24,
    @StringRes override val title: Int = R.string.date_time_trigger_title,
    var timeInMillis: Long = -1
) : Trigger(icon, title) {

    override fun schedule(appContext: Context, macro: Macro) {
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(appContext, AlarmReceiver::class.java)
        val bundle = Bundle().apply {
            putByteArray(AlarmReceiver.MACRO, macro.toByteArray())
        }
        intent.putExtras(bundle)
        intent.action = this.javaClass.simpleName

        val alarmPendingIntent = PendingIntent.getBroadcast(
            appContext,
            macro.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            alarmPendingIntent
        )
    }

    override fun cancel(appContext: Context, macro: Macro) {
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(appContext, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            appContext,
            macro.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(alarmPendingIntent)
    }

    override fun onClick(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(res =R.string.pick_date_and_time)
            dateTimePicker(
                show24HoursView = true,
                requireFutureDateTime = true,
                autoFlipToTime = true
            ) { _, dateTime ->
                timeInMillis = dateTime.timeInMillis
                super.onClick(context, macro)
            }
        }
    }
}