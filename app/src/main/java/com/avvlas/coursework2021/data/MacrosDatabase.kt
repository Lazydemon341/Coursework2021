package com.avvlas.coursework2021.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.avvlas.coursework2021.data.entities.MacroEntity
import com.avvlas.coursework2021.domain.model.Macro

@Database(entities = [MacroEntity::class], version = 1, exportSchema = false)
abstract class MacrosDatabase : RoomDatabase() {

    abstract val macrosDao: MacrosDao

    companion object {
        private const val NAME = "macros_database"

        @Volatile
        private var instance: MacrosDatabase? = null

        fun getInstance(context: Context): MacrosDatabase =
            instance ?: synchronized(this) { instance ?: build(context) }

        private fun build(context: Context): MacrosDatabase =
            Room.databaseBuilder(context, MacrosDatabase::class.java, NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}