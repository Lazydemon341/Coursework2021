package com.avvlas.coursework2021.model.options.triggers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.parcelize.Parcelize

@Parcelize
class RingerModeChangeTrigger(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_volume_up_24,
    @StringRes override val title: Int = R.string.ringer_mode_trigger_title,
    var mode: Mode = Mode.NORMAL
) : Trigger(icon, title) {

    override fun schedule(appContext: Context, macro: Macro) {
        if (receiver == null) {
            receiver = object : RingerModeChangeReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action == AudioManager.RINGER_MODE_CHANGED_ACTION
                        && !isInitialStickyBroadcast
                    ) {
                        val mode = intent.getIntExtra(
                            AudioManager.EXTRA_RINGER_MODE,
                            AudioManager.ERROR
                        )
                        for (pair in macrosWithRingerModes) {
                            val flag: Boolean = when (pair.second) {
                                Mode.NORMAL -> mode == AudioManager.RINGER_MODE_NORMAL
                                Mode.VIBRATE -> mode == AudioManager.RINGER_MODE_VIBRATE
                                Mode.SILENT -> mode == AudioManager.RINGER_MODE_SILENT
                                Mode.CHANGED -> true
                            }
                            if (flag) {
                                pair.first.runActions(context)
                            }
                        }
                    }
                }
            }
            appContext.registerReceiver(
                receiver,
                IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
            )
        }
        receiver!!.macrosWithRingerModes.add(Pair(macro, mode))
    }

    override fun cancel(context: Context, macro: Macro) {
        receiver?.let {
            it.macrosWithRingerModes.removeAll { pair ->
                pair.first == macro
            }
            if (it.macrosWithRingerModes.size == 0) {
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
                    "Sound On",
                    "Sound Off, Vibration On",
                    "Sound Off, Vibration Off",
                    "Sound Mode Changed"
                ), initialSelection = mode.ordinal
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

    companion object {
        private var receiver: RingerModeChangeReceiver? = null
    }
}

private abstract class RingerModeChangeReceiver : BroadcastReceiver() {
    val macrosWithRingerModes = arrayListOf<Pair<Macro, RingerModeChangeTrigger.Mode>>()
}

