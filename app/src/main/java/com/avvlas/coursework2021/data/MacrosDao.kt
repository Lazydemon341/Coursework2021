package com.avvlas.coursework2021.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.avvlas.coursework2021.data.entities.MacroEntity

@Dao
interface MacrosDao {

    @Query("SELECT * FROM macros ORDER BY name ASC")
    fun getAllWithUpdates(): LiveData<List<MacroEntity>>

    @Query("SELECT * FROM macros ORDER BY name ASC")
    fun getAll(): List<MacroEntity>

    @Insert // TODO: onconflict?
    fun insert(macro: MacroEntity)

    @Update
    fun update(macro: MacroEntity)

    @Delete
    fun delete(macro: MacroEntity)
}