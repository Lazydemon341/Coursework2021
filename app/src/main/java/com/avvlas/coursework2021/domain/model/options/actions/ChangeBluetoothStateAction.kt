package com.avvlas.coursework2021.domain.model.options.actions

import android.content.Context
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import kotlinx.parcelize.Parcelize

@Parcelize
class ChangeBluetoothStateAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_bluetooth_24,
    override val title: String = "Bluetooth On/Off",
    private var enable: Boolean = false
) : Action(icon, title) {

    override fun execute(context: Context) {
        TODO("Not yet implemented")
    }

    override fun onClick(context: Context, macro: Macro) {
        super.onClick(context, macro)
    }
}