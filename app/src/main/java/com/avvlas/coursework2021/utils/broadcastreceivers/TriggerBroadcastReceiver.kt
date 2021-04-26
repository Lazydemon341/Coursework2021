package com.avvlas.coursework2021.utils.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.triggers.DayTimeTrigger
import com.avvlas.coursework2021.utils.Parcelables.toParcelable
import com.avvlas.coursework2021.utils.Utils.CREATOR
import java.util.*

class TriggerBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val triggerType = intent?.getStringExtra(TRIGGER_TYPE)
        val macro = intent?.getByteArrayExtra(MACRO)?.toParcelable(Macro.CREATOR)

        // TODO: this should be done in a service?
        when (triggerType) {
            DATETIME_TRIGGER -> {
                Toast.makeText(context, "Date Trigger: ${macro.toString()}", Toast.LENGTH_SHORT)
                    .show()
                context?.let {
                    macro?.runActions(it)
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
        const val TRIGGER_TYPE = "trigger_type"
        const val DATETIME_TRIGGER = "date_time_trigger"
    }
}