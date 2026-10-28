package com.sinamtd.sixappcompose.domain.usecase

import com.sinamtd.sixappcompose.data.remote.dto.GeocodingResult
import com.sinamtd.sixappcompose.domain.repository.GeocodingRepository
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val geocodingRepository: GeocodingRepository
){
    suspend operator fun invoke(query: String): Result<List<GeocodingResult>>{
        if (query.isBlank()) return Result.success(emptyList())
        return geocodingRepository.searchCity( query)
    }
}