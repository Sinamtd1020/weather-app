package com.sinamtd.sixappcompose.data.remote.datasource

import android.util.Log
import com.sinamtd.sixappcompose.data.remote.api.WeatherApi
import com.sinamtd.sixappcompose.data.remote.dto.WeatherDto
import javax.inject.Inject
import kotlin.math.log

class WeatherRemoteDataSource @Inject constructor(private val api: WeatherApi){
    suspend fun getCurrentWeather(lat: Double,lon: Double): WeatherDto{
         val response=api.getCurrentWeather(lat,lon)
        return response
    }
}