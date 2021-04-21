package com.avvlas.coursework2021.domain.model.options.actions

import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import androidx.annotation.DrawableRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import kotlinx.parcelize.Parcelize


@Parcelize
class ChangeRingerModeAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_volume_up_24,
    override val title: String = "Sound mode",
    private var mode: Int = AudioManager.RINGER_MODE_NORMAL
) : Action(icon, title) {

    override fun execute(context: Context) {
        val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager
        audioManager.ringerMode = mode
    }

    override fun onClick(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(text = "Choose action type")
            listItemsSingleChoice(
                items = listOf(
                    "Sound and Vibration",
                    "Vibration only",
                    "Silent"
                ), initialSelection = 0
            ) { _, choice, _ ->
                when (choice) {
                    0 -> mode = AudioManager.RINGER_MODE_NORMAL
                    1 -> mode = AudioManager.RINGER_MODE_VIBRATE
                    2 -> mode = AudioManager.RINGER_MODE_SILENT
                }
            }
            positiveButton(text = "OK") {
                super.onClick(context, macro)
            }
            negativeButton(text = "CANCEL")
        }
    }
}