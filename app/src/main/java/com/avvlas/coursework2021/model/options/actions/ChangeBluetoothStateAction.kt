package com.avvlas.coursework2021.model.options.actions

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import androidx.annotation.DrawableRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.parcelize.Parcelize


@Parcelize
class ChangeBluetoothStateAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_bluetooth_24,
    override val title: String = "Bluetooth",
    private var mode: Mode = Mode.TURN_OFF
) : Action(icon, title) {

    override suspend fun execute(context: Context) {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        when (mode) {
            Mode.TURN_OFF -> adapter.disable()
            Mode.TURN_ON -> adapter.enable()
            Mode.CHANGE ->
                if (adapter.isEnabled) adapter.disable() else adapter.enable()
        }
    }

    override fun onClick(activity: Activity, macro: Macro) {
        MaterialDialog(activity).show {
            title(text = "Choose action type")
            listItemsSingleChoice(
                items = listOf(
                    "Turn off",
                    "Turn on",
                    "Change"
                ), initialSelection = 0
            ) { _, choice, _ ->
                when (choice) {
                    0 -> mode = Mode.TURN_OFF
                    1 -> mode = Mode.TURN_ON
                    2 -> mode = Mode.CHANGE
                }
            }
            positiveButton(text = "OK") {
                super.onClick(activity, macro)
            }
            negativeButton(text = "CANCEL")
        }
    }

    enum class Mode {
        TURN_OFF,
        TURN_ON,
        CHANGE
    }
}