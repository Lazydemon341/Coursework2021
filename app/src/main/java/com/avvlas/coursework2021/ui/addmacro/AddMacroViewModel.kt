package com.avvlas.coursework2021.ui.addmacro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.data.MacrosRepository
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.Category
import com.avvlas.coursework2021.model.options.actions.*
import com.avvlas.coursework2021.model.options.triggers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMacroViewModel @Inject constructor(
    private val macrosRepository: MacrosRepository
) : ViewModel() {

    internal var macro = Macro()
    internal var isNewMacro = true

    internal val triggers =
        arrayListOf(
            Category(
                R.drawable.ic_baseline_watch_24, R.string.date_time, arrayListOf(
                    DayTimeTrigger(),
                    DateTimeTrigger()
                )
            ),
            Category(
                R.drawable.ic_baseline_battery_full_24, R.string.battery, arrayListOf(
                    BatteryChargingTrigger(),
                    BatteryLevelTrigger()
                )
            ),
            Category(
                R.drawable.ic_baseline_settings_24, R.string.device_settings, arrayListOf(
                    BluetoothStateChangeTrigger(),
                    RingerModeChangeTrigger(),
                )
            )
        )

    internal val actions =
        arrayListOf(
            Category(
                R.drawable.ic_baseline_settings_24,
                R.string.device_settings,
                arrayListOf(
                    ChangeAutoRotateAction(),
                    ChangeBluetoothStateAction(),
                    ChangeRingerModeAction(),
                    ChangeWifiStateAction()
                )
            ),
            Category(
                R.drawable.ic_baseline_menu_24,
                R.string.other,
                arrayListOf(
                    TurnOnFlashlightAction()
                )
            )

        )

    fun saveMacro() =
        viewModelScope.launch {
            if (isNewMacro)
                macro.id = macrosRepository.insert(macro)
            else
                macrosRepository.update(macro)
        }
}