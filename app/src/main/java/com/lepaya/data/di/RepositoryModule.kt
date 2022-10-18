package com.lepaya.data.di

import com.lepaya.data.api.RestAPI
import com.lepaya.data.repositories.TrainersRepositoryImpl
import com.lepaya.domain.repositories.TrainersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideTrainerRepository(restAPI: RestAPI): TrainersRepository {
        return TrainersRepositoryImpl(restAPI)
    }
}