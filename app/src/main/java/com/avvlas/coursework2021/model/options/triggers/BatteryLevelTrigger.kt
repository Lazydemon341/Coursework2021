package com.avvlas.coursework2021.model.options.triggers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.parcelize.Parcelize

@Parcelize
class BatteryLevelTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_battery_full_24,
    @StringRes override val title: Int = R.string.battery_level_trigger_title,
    private var lowLevel: Boolean = false
) : Trigger(icon, title) {

    override fun schedule(appContext: Context, macro: Macro) {
        if (receiver == null) {
            receiver = object : BatteryLevelReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action == ACTION_BATTERY_LOW
                        || intent.action == ACTION_BATTERY_OKAY
                    ) {
                        for (pair in macrosWithBatteryLevel) {

                            val flag =
                                if (intent.action == ACTION_BATTERY_LOW)
                                    lowLevel
                                else
                                    !lowLevel

                            if (flag) {
                                pair.first.runActions(context)
                            }
                        }
                    }
                }
            }
            appContext.registerReceiver(
                receiver,
                IntentFilter().apply {
                    addAction(ACTION_BATTERY_LOW)
                    addAction(ACTION_BATTERY_OKAY)
                }
            )
        }
        receiver!!.macrosWithBatteryLevel.add(Pair(macro, lowLevel))
    }

    override fun cancel(appContext: Context, macro: Macro) {
        receiver?.let {
            it.macrosWithBatteryLevel.removeAll { pair ->
                pair.first.id == macro.id
            }
            if (it.macrosWithBatteryLevel.isEmpty()) {
                appContext.applicationContext.unregisterReceiver(it)
                receiver = null
            }
        }
    }

    override fun onClick(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(res = R.string.choose_trigger)
            listItemsSingleChoice(
                res = R.array.battery_level_trigger_mode,
                initialSelection = if (lowLevel) 0 else 1
            ) { B, choice, _ ->
                when (choice) {
                    0 -> lowLevel = true
                    1 -> lowLevel = false
                }
            }
            positiveButton(res = R.string.ok) {
                super.onClick(context, macro)
            }
            negativeButton(res = R.string.cancel)
        }
    }

    companion object {

        private const val ACTION_BATTERY_LOW =
            "android.intent.action.BATTERY_LOW"
        private const val ACTION_BATTERY_OKAY =
            "android.intent.action.BATTERY_OKAY"

        private var receiver: BatteryLevelReceiver? = null
    }
}

private abstract class BatteryLevelReceiver : BroadcastReceiver() {
    val macrosWithBatteryLevel = arrayListOf<Pair<Macro, Boolean>>()
}