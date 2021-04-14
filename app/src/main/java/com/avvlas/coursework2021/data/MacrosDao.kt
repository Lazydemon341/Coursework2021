package com.avvlas.coursework2021.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.avvlas.coursework2021.data.entities.MacroEntity
import com.avvlas.coursework2021.domain.model.Macro

@Dao
interface MacrosDao {

    @Query("SELECT * FROM macros ORDER BY name ASC")
    fun getAll(): LiveData<List<MacroEntity>>

    @Insert // TODO: onconflict?
    fun insert(macro: MacroEntity)
}