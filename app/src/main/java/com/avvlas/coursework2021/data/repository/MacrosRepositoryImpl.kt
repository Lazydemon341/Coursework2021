package com.avvlas.coursework2021.data.repository

import com.avvlas.coursework2021.data.MacrosDao
import com.avvlas.coursework2021.data.entities.toMacroEntity
import com.avvlas.coursework2021.domain.model.Macro
import com.avvlas.coursework2021.domain.repository.MacrosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MacrosRepositoryImpl @Inject constructor(
    private val macrosDao: MacrosDao
) : MacrosRepository {

    override suspend fun insert(macro: Macro) = withContext(Dispatchers.IO) {
        macrosDao.insert(macro.toMacroEntity())
    }
}