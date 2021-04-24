package com.avvlas.coursework2021.domain.model.options.triggers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import androidx.annotation.DrawableRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class RingerModeChangeTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_volume_up_24,
    override val title: String = "Sound Mode",
    var mode: Mode = Mode.NORMAL
) : Trigger(icon, title) {

    @IgnoredOnParcel
    private var receiver: BroadcastReceiver? = null

    override fun schedule(context: Context, macro: Macro) {
        if (receiver == null) {
            receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action == AudioManager.RINGER_MODE_CHANGED_ACTION) {
                        val mode = intent.getIntExtra(
                            AudioManager.EXTRA_RINGER_MODE,
                            AudioManager.ERROR
                        )
                        val flag: Boolean = when (this@RingerModeChangeTrigger.mode) {
                            Mode.NORMAL -> mode == AudioManager.RINGER_MODE_NORMAL
                            Mode.VIBRATE -> mode == AudioManager.RINGER_MODE_VIBRATE
                            Mode.SILENT -> mode == AudioManager.RINGER_MODE_SILENT
                            Mode.CHANGED -> true
                        }
                        if (flag) {
                            GlobalScope.launch(Dispatchers.Default) {
                                for (action in macro.actions) {
                                    action.execute(context)
                                }
                            }
                        }
                    }
                }
            }
            context.registerReceiver(
                receiver,
                IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
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
                    "Sound On",
                    "Sound Off, Vibration On",
                    "Sound Off, Vibration Off",
                    "Sound Mode Changed"
                ), initialSelection = 0
            ) { _, choice, _ ->
                when (choice) {
                    0 -> mode = Mode.NORMAL
                    1 -> mode = Mode.VIBRATE
                    2 -> mode = Mode.SILENT
                    3 -> mode = Mode.CHANGED
                }
            }
            positiveButton(text = "OK") {
                super.onClick(context, macro)
            }
            negativeButton(text = "CANCEL")
        }
    }

    enum class Mode {
        NORMAL, VIBRATE, SILENT, CHANGED
    }
}

private abstract class BaseRingerModeChangeReceiver : BroadcastReceiver() {
    val macrosWithBluetoothModes = arrayListOf<Pair<Macro, RingerModeChangeTrigger.Mode>>()
}

