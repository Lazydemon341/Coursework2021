package com.avvlas.coursework2021.model.options.actions

import android.bluetooth.BluetoothAdapter
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.parcelize.Parcelize


@Parcelize
class ChangeBluetoothStateAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_bluetooth_24,
    @StringRes override val title: Int = R.string.change_bluetooth_state_action_title,
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

    override fun onClick(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(res = R.string.choose_action)
            listItemsSingleChoice(
                res = R.array.bluetooth_state_action_mode,
                initialSelection = mode.ordinal
            ) { _, choice, _ ->
                when (choice) {
                    0 -> mode = Mode.TURN_OFF
                    1 -> mode = Mode.TURN_ON
                    2 -> mode = Mode.CHANGE
                }
            }
            positiveButton(res = R.string.ok) {
                super.onClick(context, macro)
            }
            negativeButton(res = R.string.cancel)
        }
    }

    enum class Mode {
        TURN_OFF,
        TURN_ON,
        CHANGE
    }
}