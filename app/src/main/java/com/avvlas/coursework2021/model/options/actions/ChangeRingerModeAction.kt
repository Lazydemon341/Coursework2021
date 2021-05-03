package com.avvlas.coursework2021.model.options.actions

import android.app.Activity
import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat.startActivity
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.parcelize.Parcelize


@Parcelize
class ChangeRingerModeAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_battery_charging_full_24,
    @StringRes override val title: Int = R.string.change_ringer_mode_action_title,
    private var mode: Int = AudioManager.RINGER_MODE_NORMAL
) : Action(icon, title) {

    override suspend fun execute(context: Context) {
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
                    2 -> {
                        requireDoNotDisturbPermission(context)
                        mode = AudioManager.RINGER_MODE_SILENT
                    }
                }
            }
            positiveButton(text = "OK") {
                super.onClick(context, macro)
            }
            negativeButton(text = "CANCEL")
        }
    }

    private fun requireDoNotDisturbPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Ask for Do Not Disturb Access permission on API 24+

            val packageManager: PackageManager = context.packageManager
            val intent = Intent()
            intent.action = DO_NOT_DISTURB_ACCESS_PERMISSION

            if (ContextCompat.checkSelfPermission(
                    context,
                    DO_NOT_DISTURB_ACCESS_PERMISSION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                // TODO: launch AlertDialog
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(context, intent, null)
                } else {
                    Log.d("ask_permission", "No Intent available to handle action")
                }

            }
        }
    }

    companion object {
        private const val DO_NOT_DISTURB_ACCESS_PERMISSION =
            "android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS"
    }
}