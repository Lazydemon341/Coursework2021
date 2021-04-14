package com.avvlas.coursework2021.di

import android.content.Context
import com.avvlas.coursework2021.data.MacrosDao
import com.avvlas.coursework2021.data.MacrosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMacrosDatabase(@ApplicationContext appContext: Context): MacrosDatabase =
        MacrosDatabase.getInstance(appContext)

    @Provides
    @Singleton
    fun provideMacrosDao(database: MacrosDatabase): MacrosDao =
        database.macrosDao
}