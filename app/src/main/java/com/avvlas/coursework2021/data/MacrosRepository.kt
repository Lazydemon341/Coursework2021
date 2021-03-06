package com.avvlas.coursework2021.data

import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.avvlas.coursework2021.data.entities.toMacro
import com.avvlas.coursework2021.data.entities.toMacroEntity
import com.avvlas.coursework2021.model.Macro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MacrosRepository @Inject constructor(
    private val macrosDao: MacrosDao
) {
    suspend fun insert(macro: Macro): Long = withContext(Dispatchers.IO) {
        macrosDao.insert(macro.toMacroEntity())
    }

    suspend fun update(macro: Macro) = withContext(Dispatchers.IO) {
        macrosDao.update(macro.toMacroEntity())
    }

    suspend fun delete(macro: Macro) = withContext(Dispatchers.IO) {
        macrosDao.delete(macro.toMacroEntity())
    }

    fun getAllWithUpdates() = liveData(Dispatchers.IO) {
        emitSource(macrosDao.getAllWithUpdates().map { entities ->
            entities.map {
                it.toMacro()
            }
        })
    }

    suspend fun getAll() = withContext(Dispatchers.IO) {
        macrosDao.getAll().map {
            it.toMacro()
        }
    }

    suspend fun getMacrosNames() = withContext(Dispatchers.IO) {
        macrosDao.getNames()
    }
}