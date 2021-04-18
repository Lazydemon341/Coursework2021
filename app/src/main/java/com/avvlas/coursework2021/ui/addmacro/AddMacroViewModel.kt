package com.avvlas.coursework2021.ui.addmacro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avvlas.coursework2021.domain.model.Macro
import com.avvlas.coursework2021.data.MacrosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMacroViewModel @Inject constructor(
    private val macrosRepository: MacrosRepository
) : ViewModel() {
    val macro = Macro()

    fun saveMacro() =
        viewModelScope.launch {
            macrosRepository.insert(macro)
        }
}