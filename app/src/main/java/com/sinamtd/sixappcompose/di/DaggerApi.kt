package com.sinamtd.sixappcompose.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sinamtd.sixappcompose.data.remote.api.AirQualityApi
import com.sinamtd.sixappcompose.data.remote.api.GeocodingApi
import com.sinamtd.sixappcompose.data.remote.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_URL="https://api.open-meteo.com/v1/"
private const val BASE_URL_AIR="https://air-quality-api.open-meteo.com/v1/"
private const val GEO_BASE_URL="https://geocoding-api.open-meteo.com/v1/"
private  val json=Json { ignoreUnknownKeys=true }

@Module
@InstallIn(SingletonComponent::class)
object DaggerApi {
    @Singleton
    @Provides
    fun provideOkHttp()= OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()



    @Singleton
    @Provides
    @Named("weather")
    fun provideWeatherRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Singleton
    @Provides
    @Named("airQuality")
    fun provideAirQualityRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_AIR)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Singleton
    @Provides
    @Named("geocoding")
    fun provideGeoRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(GEO_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Singleton
    @Provides
    fun provideWeatherApi(@Named("weather") retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Singleton
    @Provides
    fun provideAirQualityApi(@Named("airQuality") retrofit: Retrofit): AirQualityApi{
        return retrofit.create(AirQualityApi::class.java)
    }


    @Singleton
    @Provides
    fun provideGeocodingApi(@Named("geocoding") retrofit: Retrofit): GeocodingApi =
        retrofit.create(GeocodingApi::class.java)
}