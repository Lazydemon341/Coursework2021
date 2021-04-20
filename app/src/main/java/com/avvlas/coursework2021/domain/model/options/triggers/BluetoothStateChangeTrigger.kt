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
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
class BluetoothStateChangeTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_calendar_today_24,
    override val title: String = "Bluetooth state",
    var enabled: Boolean = false
) : Trigger(icon, title) {

    @IgnoredOnParcel
    private var receiver: BroadcastReceiver? = null

    override fun schedule(context: Context, macro: Macro) {
        if (receiver == null) {
            receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                        val state = intent.getIntExtra(
                            BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.ERROR
                        )
                        if (enabled && (state == BluetoothAdapter.STATE_ON || state == BluetoothAdapter.STATE_TURNING_ON) ||
                            !enabled && (state == BluetoothAdapter.STATE_OFF || state == BluetoothAdapter.STATE_TURNING_OFF)
                        ) {
                            for (action in macro.actions) {
                                action.execute(context)
                            }
                        }
                    }
                    context.unregisterReceiver(this)
                }
            }
            context.registerReceiver(
                receiver,
                IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            )
        }
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
                    "Bluetooth Off",
                    "Bluetooth On"
                ), initialSelection = 0
            ) { _, choice, _ ->
                when (choice) {
                    0 -> enabled = false
                    1 -> enabled = true
                }
            }
            positiveButton(text = "OK") {
                super.onClick(context, macro)
            }
            negativeButton(text = "CANCEL")
        }
    }
}