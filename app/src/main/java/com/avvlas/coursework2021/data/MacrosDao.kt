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

    @Query("SELECT name FROM macros")
    fun getNames(): List<String>

    @Insert
    fun insert(macro: MacroEntity): Long

    @Update
    fun update(macro: MacroEntity)

    @Delete
    fun delete(macro: MacroEntity)
}