package com.avvlas.coursework2021.ui.macroslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avvlas.coursework2021.data.MacrosRepository
import com.avvlas.coursework2021.model.Macro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MacrosListViewModel @Inject constructor(
    private val macrosRepository: MacrosRepository
) : ViewModel() {
    internal val macros = macrosRepository.getAllWithUpdates()

    internal fun updateMacro(macro: Macro) = viewModelScope.launch {
        macrosRepository.update(macro)
    }
}