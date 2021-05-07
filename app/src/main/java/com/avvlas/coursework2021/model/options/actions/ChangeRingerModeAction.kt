package com.avvlas.coursework2021.model.options.actions

import android.app.NotificationManager
import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat.startActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
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
            title(res = R.string.choose_action)
            listItemsSingleChoice(
                items = listOf(
                    "Sound and Vibration",
                    "Vibration only",
                    "Silent"
                ), initialSelection = when (mode) {
                    AudioManager.RINGER_MODE_NORMAL -> 0
                    AudioManager.RINGER_MODE_VIBRATE -> 1
                    AudioManager.RINGER_MODE_SILENT -> 2
                    else -> 0
                },
                waitForPositiveButton = false
            ) { dialog, choice, _ ->
                onChoice(context, dialog, choice)
            }
            positiveButton(res = R.string.ok) {
                super.onClick(context, macro)
            }
            negativeButton(res = R.string.cancel)
        }
    }

    private fun onChoice(context: Context, dialog: MaterialDialog, choice: Int) {
        dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
        when (choice) {
            0 -> mode =
                AudioManager.RINGER_MODE_NORMAL
            1 -> mode =
                AudioManager.RINGER_MODE_VIBRATE
            2 -> {
                if (!(context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).isNotificationPolicyAccessGranted) {
                    // Disable positive button if permission not granted
                    dialog.setActionButtonEnabled(WhichButton.POSITIVE, false)
                    requireDoNotDisturbPermission(context)
                } else {
                    mode = AudioManager.RINGER_MODE_SILENT
                }
            }
        }
    }

    private fun requireDoNotDisturbPermission(context: Context) =
        MaterialDialog(context).show {
            title(res = R.string.permission_required)
            message(res = R.string.do_not_disturb_permission_required)
            positiveButton(res = R.string.ok) {
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && !notificationManager.isNotificationPolicyAccessGranted
                ) {
                    val intent = Intent(
                        Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS
                    )
                    startActivity(context, intent, null)
                }
            }
            negativeButton(res = R.string.cancel)
        }
}