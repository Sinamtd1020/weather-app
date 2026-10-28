package com.sinamtd.sixappcompose.data.repository

import com.sinamtd.sixappcompose.data.remote.api.AirQualityApi
import com.sinamtd.sixappcompose.data.remote.dto.AirQualityResponse
import com.sinamtd.sixappcompose.domain.repository.ApiRepository
import javax.inject.Inject

class AirQualityRepositoryImpl  @Inject constructor(private val api: AirQualityApi,
    private val apiRepository: ApiRepository){
    suspend fun getAirQuality(lat: Double,lan: Double): Result<AirQualityResponse>{
        return apiRepository.getSafe {
            api.getAirQuality(latitude = lat, longitude = lan)
        }
    }
}