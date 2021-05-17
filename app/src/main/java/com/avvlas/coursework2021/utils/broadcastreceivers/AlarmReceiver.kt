package com.avvlas.coursework2021.utils.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.triggers.ExactDateTimeTrigger
import com.avvlas.coursework2021.model.options.triggers.RepeatingTimeTrigger
import com.avvlas.coursework2021.utils.Parcelables.toParcelable
import com.avvlas.coursework2021.utils.Utils.CREATOR
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AlarmReceiver", intent.action.toString())
        when (intent.action) {
            ExactDateTimeTrigger::class.java.simpleName -> {
                runMacroActions(context, intent)
            }
            RepeatingTimeTrigger::class.java.simpleName -> {
                runMacroActions(context, intent)
            }
        }
    }

    private fun runMacroActions(context: Context, intent: Intent) {
        val macro = intent.getByteArrayExtra(MACRO)?.toParcelable(Macro.CREATOR)
        macro?.runActions(context)
    }

    private fun isDayTimeTriggerToday(repeatingTimeTrigger: RepeatingTimeTrigger): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        return when (calendar[Calendar.DAY_OF_WEEK]) {
            Calendar.MONDAY -> repeatingTimeTrigger.days[0]
            Calendar.TUESDAY -> repeatingTimeTrigger.days[1]
            Calendar.WEDNESDAY -> repeatingTimeTrigger.days[2]
            Calendar.THURSDAY -> repeatingTimeTrigger.days[3]
            Calendar.FRIDAY -> repeatingTimeTrigger.days[4]
            Calendar.SATURDAY -> repeatingTimeTrigger.days[5]
            Calendar.SUNDAY -> repeatingTimeTrigger.days[6]
            else -> false
        }
    }


    companion object {
        const val MACRO = "macro"
    }
}