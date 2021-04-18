package com.avvlas.coursework2021.ui.macroslist

import androidx.lifecycle.ViewModel
import com.avvlas.coursework2021.data.MacrosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MacrosListViewModel @Inject constructor(
    macrosRepository: MacrosRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
    val macros = macrosRepository.getAllWithUpdates()
}