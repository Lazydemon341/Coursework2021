package com.avvlas.coursework2021.domain.repository

import com.avvlas.coursework2021.domain.model.Macro

interface MacrosRepository {
    suspend fun insert(macro : Macro)
}