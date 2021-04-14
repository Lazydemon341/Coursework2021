package com.avvlas.coursework2021.data

import com.avvlas.coursework2021.domain.model.Macro
import com.avvlas.coursework2021.domain.repository.MacrosRepository
import javax.inject.Inject

class MacrosRepositoryImpl @Inject constructor(
    private val macrosDao: MacrosDao
) : MacrosRepository {

    override fun insert(macro: Macro) {
        TODO("Not yet implemented")
    }
}