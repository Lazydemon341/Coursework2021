package com.avvlas.coursework2021.utils.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.avvlas.coursework2021.domain.model.options.triggers.DayTimeTrigger
import java.util.*

class TriggerBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val trigger = intent?.extras?.get(TRIGGER)
        when (trigger) {
            is DayTimeTrigger -> {
                if (isDayTimeTriggerToday(trigger)) {
                    Toast.makeText(context, "Day/Time Trigger", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isDayTimeTriggerToday(dayTimeTrigger: DayTimeTrigger): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        return when (calendar[Calendar.DAY_OF_WEEK]) {
            Calendar.MONDAY -> dayTimeTrigger.days[0]
            Calendar.TUESDAY -> dayTimeTrigger.days[1]
            Calendar.WEDNESDAY -> dayTimeTrigger.days[2]
            Calendar.THURSDAY -> dayTimeTrigger.days[3]
            Calendar.FRIDAY -> dayTimeTrigger.days[4]
            Calendar.SATURDAY -> dayTimeTrigger.days[5]
            Calendar.SUNDAY -> dayTimeTrigger.days[6]
            else -> false
        }
    }

    companion object {
        const val MACRO = "macro"
        const val TRIGGER = "trigger"
    }
}