package com.avvlas.coursework2021.domain.model.options.triggers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.DrawableRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import kotlinx.parcelize.Parcelize

@Parcelize
class BatteryChargingTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_calendar_today_24,
    override val title: String = "Battery Charging",
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

    override fun cancel(context: Context, macro: Macro) {
        receiver?.let {
            it.macrosWithConnectionState.removeAll { pair ->
                pair.first == macro
            }
            if (it.macrosWithConnectionState.isEmpty()) {
                context.unregisterReceiver(it)
                receiver = null
            }
        }
    }

    override fun onClick(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(text = "Choose trigger type")
            listItemsSingleChoice(
                items = listOf(
                    "Power Connected",
                    "Power Disconnected"
                ), initialSelection = 0
            ) { _, choice, _ ->
                when (choice) {
                    0 -> connected = true
                    1 -> connected = false
                }
            }
            positiveButton(text = "OK") {
                super.onClick(context, macro)
            }
            negativeButton(text = "CANCEL")
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
