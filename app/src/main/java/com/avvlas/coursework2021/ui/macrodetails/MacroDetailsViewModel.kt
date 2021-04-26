package com.avvlas.coursework2021.ui.macrodetails

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.avvlas.coursework2021.data.MacrosRepository
import com.avvlas.coursework2021.model.Macro
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MacroDetailsViewModel @AssistedInject constructor(
    private val macrosRepository: MacrosRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    internal val macro = savedStateHandle.get<Macro>(MacroDetailsFragment.ARG_MACRO)
        ?: throw IllegalArgumentException("Macro required")

    private val mutableDeletionState = MutableLiveData<Boolean>(false)
    internal val deletionState: LiveData<Boolean> get() = mutableDeletionState

    internal fun updateMacro() = viewModelScope.launch {
        macrosRepository.update(macro)
    }

    internal fun deleteMacro() = viewModelScope.launch {
        macrosRepository.delete(macro)
        mutableDeletionState.value = true
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