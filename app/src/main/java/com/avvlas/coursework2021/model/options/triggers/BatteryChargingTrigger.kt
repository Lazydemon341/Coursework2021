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
class BatteryChargingTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_battery_charging_full_24,
    @StringRes override val title: Int = R.string.battery_charging_trigger_title,
    private var connected: Boolean = false
) : Trigger(icon, title) {

    override fun schedule(appContext: Context, macro: Macro) {
        if (receiver == null) {
            receiver = object : PowerConnectionReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action == ACTION_POWER_CONNECTED
                        || intent.action == ACTION_POWER_DISCONNECTED
                    ) {
                        for (pair in macrosWithConnectionState) {

                            val flag =
                                if (intent.action == ACTION_POWER_CONNECTED)
                                    connected
                                else
                                    !connected

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
                    addAction(ACTION_POWER_CONNECTED)
                    addAction(ACTION_POWER_DISCONNECTED)
                }
            )
        }
        receiver!!.macrosWithConnectionState.add(Pair(macro, connected))
    }

    override fun cancel(appContext: Context, macro: Macro) {
        receiver?.let {
            it.macrosWithConnectionState.removeAll { pair ->
                pair.first.id == macro.id
            }
            if (it.macrosWithConnectionState.isEmpty()) {
                appContext.applicationContext.unregisterReceiver(it)
                receiver = null
            }
        }
    }

    override fun onClick(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(res = R.string.choose_trigger)
            listItemsSingleChoice(
                res = R.array.battery_charging_trigger_mode,
                initialSelection = if (connected) 0 else 1
            ) { _, choice, _ ->
                connected = choice == 0
            }
            positiveButton(res = R.string.ok) {
                super.onClick(context, macro)
            }
            negativeButton(R.string.cancel)
        }
    }

    companion object {

        private const val ACTION_POWER_CONNECTED =
            "android.intent.action.ACTION_POWER_CONNECTED"
        private const val ACTION_POWER_DISCONNECTED =
            "android.intent.action.ACTION_POWER_DISCONNECTED"

        private var receiver: PowerConnectionReceiver? = null
    }
}

private abstract class PowerConnectionReceiver : BroadcastReceiver() {
    val macrosWithConnectionState = arrayListOf<Pair<Macro, Boolean>>()
}
