package com.avvlas.coursework2021.ui.addmacro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.data.MacrosRepository
import com.avvlas.coursework2021.domain.model.Macro
import com.avvlas.coursework2021.domain.model.options.Category
import com.avvlas.coursework2021.domain.model.options.actions.*
import com.avvlas.coursework2021.domain.model.options.triggers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMacroViewModel @Inject constructor(
    private val macrosRepository: MacrosRepository
) : ViewModel() {

    internal val macro = Macro()

    internal val triggers =
        arrayListOf(
            Category<Trigger>(
                R.drawable.ic_baseline_watch_24, "Category1", arrayListOf(
                    DayTimeTrigger(),
                    DateTimeTrigger(),
                    BluetoothStateChangeTrigger(),
                    RingerModeChangeTrigger(),
                    PowerConnectionTrigger()
                    //WifiStateChangeTrigger()
                )
            ),
            Category<Trigger>(
                R.drawable.ic_baseline_watch_24, "Category2", arrayListOf(
                    LocationTrigger(R.drawable.ic_baseline_watch_24, "trigger1"),
                    LocationTrigger(R.drawable.ic_baseline_check_24, "trigger2")
                )
            ),
            Category<Trigger>(R.drawable.ic_baseline_watch_24, "Category3", arrayListOf())
        )

    internal val actions =
        arrayListOf(
            Category<Action>(
                R.drawable.ic_baseline_circle_notifications_24,
                "Notifications",
                arrayListOf(
                    ChangeAutoRotateAction(),
                    ChangeWifiStateAction(),
                    ChangeBluetoothStateAction(),
                    ChangeRingerModeAction()
                )
            ),
            Category<Action>(R.drawable.ic_baseline_watch_24, "Category1", arrayListOf()),
            Category<Action>(R.drawable.ic_baseline_watch_24, "Category1", arrayListOf())
        )

    fun saveMacro() =
        viewModelScope.launch {
            macrosRepository.insert(macro)
        }


}