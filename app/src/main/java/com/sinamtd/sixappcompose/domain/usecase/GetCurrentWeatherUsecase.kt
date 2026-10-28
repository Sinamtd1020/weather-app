package com.sinamtd.sixappcompose.domain.usecase

import com.sinamtd.sixappcompose.data.remote.dto.WeatherDto
import com.sinamtd.sixappcompose.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val repository: WeatherRepository){
    suspend operator fun invoke(city: String): Result<WeatherDto>{
        return repository.getCurrentWeather(city)
    }
}