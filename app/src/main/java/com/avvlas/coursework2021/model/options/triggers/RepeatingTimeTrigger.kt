package com.avvlas.coursework2021.model.options.triggers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.timePicker
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.utils.broadcastreceivers.AlarmReceiver
import kotlinx.parcelize.Parcelize
import java.text.DateFormatSymbols
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

    override fun cancel(appContext: Context, macro: Macro) {
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(appContext, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(appContext, macro.hashCode(), intent, 0)
        alarmManager.cancel(alarmPendingIntent)
    }

    override fun onClick(context: Context, macro: Macro) {
        chooseTime(context, macro)
    }

    private fun chooseTime(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(res = R.string.pick_time)
            timePicker(requireFutureTime = false, show24HoursView = true) { dialog, datetime ->
                hour = datetime.get(Calendar.HOUR_OF_DAY)
                minute = datetime.get(Calendar.MINUTE)
            }
            positiveButton(res = R.string.ok) {
                chooseDays(context, macro)
            }
            negativeButton(res = R.string.cancel)
        }
    }

    private fun chooseDays(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(res = R.string.pick_days_of_week)
            listItemsMultiChoice(
                items = DateFormatSymbols.getInstance().shortWeekdays.filter { it.isNotEmpty() },
                allowEmptySelection = false
            )
            positiveButton(res = R.string.ok) {
                // TODO: set days
                super.onClick(context, macro)
            }
            negativeButton(res = R.string.cancel)
        }
    }
}