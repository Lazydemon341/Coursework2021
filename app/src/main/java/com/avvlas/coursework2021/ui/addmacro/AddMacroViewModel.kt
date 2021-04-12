package com.avvlas.coursework2021.ui.addmacro

import androidx.lifecycle.ViewModel
import com.avvlas.coursework2021.domain.model.Macro
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddMacroViewModel @Inject constructor(
    // TODO
) : ViewModel() {
    private val macro = Macro()
}