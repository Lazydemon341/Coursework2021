package com.avvlas.coursework2021.domain.model.options.triggers

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.DrawableRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize


@Parcelize
class BluetoothStateChangeTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_calendar_today_24,
    override val title: String = "Bluetooth",
    var mode: Mode = Mode.CHANGED
) : Trigger(icon, title) {

    override fun schedule(context: Context, macro: Macro) {
        if (receiver == null) {
            receiver = object : BaseBluetoothStateChangeReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                        val state = intent.getIntExtra(
                            BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.ERROR
                        )
                        for (pair in macrosWithBluetoothModes) {
                            val flag = when (pair.second) {
                                Mode.ON ->
                                    state == BluetoothAdapter.STATE_ON
                                            || state == BluetoothAdapter.STATE_TURNING_ON
                                Mode.OFF ->
                                    state == BluetoothAdapter.STATE_OFF
                                            || state == BluetoothAdapter.STATE_TURNING_OFF
                                Mode.CHANGED -> true
                            }
                            if (flag) {
                                GlobalScope.launch(Dispatchers.Default) {
                                    for (action in pair.first.actions) {
                                        action.execute(context)
                                    }
                                }
                            }

                        }
                    }
                }
            }
            context.registerReceiver(
                receiver,
                IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            )
        }
        receiver!!.macrosWithBluetoothModes.add(Pair(macro, mode))
    }

    override fun cancel(context: Context, macro: Macro) {
        receiver?.let {
            context.unregisterReceiver(it)
            receiver = null
        }
    }

    override fun onClick(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(text = "Choose trigger type")
            listItemsSingleChoice(
                items = listOf(
                    "Bluetooth On",
                    "Bluetooth Off",
                    "Bluetooth On/Off"
                ), initialSelection = 0
            ) { _, choice, _ ->
                when (choice) {
                    0 -> mode = Mode.ON
                    1 -> mode = Mode.OFF
                    2 -> mode = Mode.CHANGED
                }
            }
            positiveButton(text = "OK") {
                super.onClick(context, macro)
            }
            negativeButton(text = "CANCEL")
        }
    }

    enum class Mode {
        ON,
        OFF,
        CHANGED
    }

    companion object {
        private var receiver: BaseBluetoothStateChangeReceiver? = null
    }
}

private abstract class BaseBluetoothStateChangeReceiver : BroadcastReceiver() {
    val macrosWithBluetoothModes = arrayListOf<Pair<Macro, BluetoothStateChangeTrigger.Mode>>()
}