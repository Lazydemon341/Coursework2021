package com.avvlas.coursework2021.utils.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.triggers.LocationTrigger
import com.avvlas.coursework2021.utils.Parcelables.toParcelable
import com.avvlas.coursework2021.utils.Utils.CREATOR

class GeofenceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("GeofenceReceiver", intent.action.toString())
        if (intent.action == LocationTrigger::class.java.simpleName) {
            val macro = intent.getByteArrayExtra(AlarmReceiver.MACRO)?.toParcelable(Macro.CREATOR)
            macro?.runActions(context)
        }
    }
}