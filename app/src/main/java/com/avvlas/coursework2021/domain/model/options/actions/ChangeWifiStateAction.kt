package com.avvlas.coursework2021.domain.model.options.actions

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat.startActivity
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import kotlinx.parcelize.Parcelize

@Parcelize
class ChangeWifiStateAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_screen_rotation_24,
    override val title: String = "Wifi On/Off",
    private var enable: Boolean = false
) : Action(icon, title) {

    override fun execute(context: Context) {
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
        // TODO: show dialog to choose action type
        super.onClick(context, macro)
    }
}