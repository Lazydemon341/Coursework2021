package com.avvlas.coursework2021.ui.macrodetails

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.avvlas.coursework2021.data.MacrosRepository
import com.avvlas.coursework2021.domain.model.Macro
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MacroDetailsViewModel @AssistedInject constructor(
    private val macrosRepository: MacrosRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    internal val macro = savedStateHandle.get<Macro>("macro")
        ?: throw IllegalArgumentException("Macro required")

    internal fun updateMacro() = viewModelScope.launch {
        macrosRepository.update(macro)
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): MacroDetailsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return assistedFactory.create(handle) as T
                }
            }
    }
}