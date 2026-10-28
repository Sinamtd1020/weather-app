package com.sinamtd.sixappcompose.data.repository

import com.sinamtd.sixappcompose.data.remote.api.GeocodingApi
import com.sinamtd.sixappcompose.data.remote.datasource.WeatherRemoteDataSource
import com.sinamtd.sixappcompose.data.remote.dto.WeatherDto
import com.sinamtd.sixappcompose.domain.repository.ApiRepository
import com.sinamtd.sixappcompose.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor
    (private val remoteDataSource: WeatherRemoteDataSource,
            private val apiRepository: ApiRepository,
            private val geocodingApi: GeocodingApi):
    WeatherRepository{
    override suspend fun getCurrentWeather(city: String): Result<WeatherDto> {
        return apiRepository.getSafe {
            val geoResponse = geocodingApi.searchLocation(city)
            val firstResult = geoResponse.results?.firstOrNull()
                ?:throw Exception("Not Found")

            remoteDataSource.getCurrentWeather(lat = firstResult.latitude, lon = firstResult.longitude)

        } }
}