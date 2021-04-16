package com.avvlas.coursework2021.domain.repository

import com.avvlas.coursework2021.data.MacrosDao
import com.avvlas.coursework2021.data.entities.toMacroEntity
import com.avvlas.coursework2021.domain.model.Macro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MacrosRepository @Inject constructor(
    private val macrosDao: MacrosDao
) {
    suspend fun insert(macro: Macro) = withContext(Dispatchers.IO) {
        macrosDao.insert(macro.toMacroEntity())
    }
}