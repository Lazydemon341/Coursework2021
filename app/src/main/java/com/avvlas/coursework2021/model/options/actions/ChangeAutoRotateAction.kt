package com.avvlas.coursework2021.model.options.actions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.parcelize.Parcelize


@Parcelize
class ChangeAutoRotateAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_screen_rotation_24,
    @StringRes override val title: Int = R.string.change_auto_rotate_action_title,
    private var enable: Boolean = false
) : Action(icon, title) {

    override suspend fun execute(context: Context) {
        if (Settings.System.canWrite(context)) {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.ACCELEROMETER_ROTATION,
                if (enable) 1 else 0
            )
        }
    }

    override fun onClick(context: Context, macro: Macro) {
        val activity = context as AppCompatActivity
        if (checkSystemWritePermission(activity)) {
            MaterialDialog(activity).show {
                title(res = R.string.choose_action)
                listItemsSingleChoice(
                    items = listOf(
                        "Enable rotation",
                        "Disable rotation"
                    ), initialSelection = if (enable) 0 else 1
                ) { _, choice, _ ->
                    when (choice) {
                        0 -> enable = false
                        1 -> enable = true
                    }
                }
                positiveButton(res = R.string.ok) {
                    super.onClick(activity, macro)
                }
                negativeButton(res = R.string.cancel)
            }
        }
    }

    private fun checkSystemWritePermission(context: Context): Boolean {
        var retVal = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(context)
            if (!retVal) {
                // Permission not granted - navigate to permission screen
                MaterialDialog(context).show {
                    title(res = R.string.permission_required)
                    message(res = R.string.write_settings_permission_required)
                    positiveButton(res = R.string.ok) {
                        openAndroidPermissionsMenu(context)
                    }
                    negativeButton(res = R.string.cancel)
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