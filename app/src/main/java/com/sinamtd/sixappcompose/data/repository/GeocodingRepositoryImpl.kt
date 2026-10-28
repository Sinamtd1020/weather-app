package com.sinamtd.sixappcompose.data.repository

import com.sinamtd.sixappcompose.data.remote.api.GeocodingApi
import com.sinamtd.sixappcompose.data.remote.dto.GeocodingResult
import com.sinamtd.sixappcompose.domain.repository.ApiRepository
import com.sinamtd.sixappcompose.domain.repository.GeocodingRepository
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(
    private val geocodingApi: GeocodingApi,
    private val apiRepository: ApiRepository
) : GeocodingRepository{
    override suspend fun searchCity(query: String): Result<List<GeocodingResult>> {
        return apiRepository.getSafe {
            val response=geocodingApi.searchLocation(query)
             response.results?:emptyList()
        }
    }
}