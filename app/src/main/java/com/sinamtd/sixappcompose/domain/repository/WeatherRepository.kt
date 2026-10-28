package com.sinamtd.sixappcompose.domain.repository

import com.sinamtd.sixappcompose.data.remote.dto.WeatherDto


interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): Result<WeatherDto>
}