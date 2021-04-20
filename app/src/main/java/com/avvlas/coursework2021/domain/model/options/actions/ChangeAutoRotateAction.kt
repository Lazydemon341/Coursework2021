package com.avvlas.coursework2021.domain.model.options.actions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat.startActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import kotlinx.parcelize.Parcelize


@Parcelize
class ChangeAutoRotateAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_screen_rotation_24,
    override val title: String = "Enable/Disable Auto Rotation",
    private var enable: Boolean = false
) : Action(icon, title) {

    override fun execute(context: Context) {
        if (Settings.System.canWrite(context)) {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.ACCELEROMETER_ROTATION,
                if (enable) 1 else 0
            )
        }
    }

    override fun onClick(context: Context, macro: Macro) {
        if (checkSystemWritePermission(context)) {
            MaterialDialog(context).show {
                title(text = "Choose action type")
                listItemsSingleChoice(
                    items = listOf(
                        "Disable rotation",
                        "Enable rotation"
                    ), initialSelection = 0
                ) { _, choice, _ ->
                    when (choice) {
                        0 -> enable = false
                        1 -> enable = true
                    }
                }
                positiveButton(text = "OK") {
                    super.onClick(context, macro)
                }
                negativeButton(text = "CANCEL")
            }
        }
    }

    private fun checkSystemWritePermission(context: Context): Boolean {
        var retVal = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(context)
            Log.d("TAG", "Can Write Settings: $retVal")
            if (!retVal) {
                // Permission not granted - navigate to permission screen
                MaterialDialog(context).show {
                    title(text = "Requires write-settings permission")
                    message(text = "This action requires the write-settings permission granted.")
                    positiveButton(text = "OK") {
                        openAndroidPermissionsMenu(context)
                    }
                    negativeButton(text = "CANCEL")
                }
            }
        }
        return retVal
    }

    private fun openAndroidPermissionsMenu(context: Context) {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package:" + context.packageName)
        startActivity(context, intent, null)
    }
}