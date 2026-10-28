package com.sinamtd.sixappcompose.di

import android.content.Context
import com.sinamtd.sixappcompose.data.datastore.UserPreferencesRepository
import com.sinamtd.sixappcompose.data.repository.GeocodingRepositoryImpl
import com.sinamtd.sixappcompose.data.repository.WeatherRepositoryImpl
import com.sinamtd.sixappcompose.domain.repository.GeocodingRepository
import com.sinamtd.sixappcompose.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
    abstract class RepositoryModule{
        @Binds
        @Singleton
        abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

        @Binds
        @Singleton
        abstract fun bindGeocodingRepository(geocodingRepositoryImpl: GeocodingRepositoryImpl): GeocodingRepository


    }