package com.avvlas.coursework2021.di

import com.avvlas.coursework2021.data.repository.MacrosRepositoryImpl
import com.avvlas.coursework2021.domain.repository.MacrosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMacrosRepository(macrosRepositoryImpl: MacrosRepositoryImpl): MacrosRepository
}