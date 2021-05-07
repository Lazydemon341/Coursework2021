package com.avvlas.coursework2021.model.options.actions

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.startActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.parcelize.Parcelize

@Parcelize
class ChangeWifiStateAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_wifi_24,
    @StringRes override val title: Int = R.string.change_wifi_state_action_title,
    private var enable: Boolean = false
) : Action(icon, title) {

    override suspend fun execute(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
            startActivity(context, panelIntent, null)
        } else {
            (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).apply {
                isWifiEnabled = enable
            }
        }
    }

    override fun onClick(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(res = R.string.choose_action)
            listItemsSingleChoice(
                items = listOf(
                    "Wifi On",
                    "Wifi Off"
                ), initialSelection = if (enable) 0 else 1
            ) { _, choice, _ ->
                when (choice) {
                    0 -> enable = true
                    1 -> enable = false
                }
            }
            positiveButton(text = "OK") {
                super.onClick(context, macro)
            }
            negativeButton(text = "CANCEL")
        }
    }
}