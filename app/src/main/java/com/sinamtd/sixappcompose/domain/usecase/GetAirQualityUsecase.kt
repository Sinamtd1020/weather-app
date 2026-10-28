package com.sinamtd.sixappcompose.domain.usecase
import com.sinamtd.sixappcompose.data.remote.dto.AirQualityResponse
import com.sinamtd.sixappcompose.data.repository.AirQualityRepositoryImpl
import javax.inject.Inject

class GetAirQualityUseCase @Inject constructor(
    private val repository: AirQualityRepositoryImpl
) {
    suspend operator fun invoke(lat: Double, lon: Double): Result<AirQualityResponse> {
        return repository.getAirQuality(lat, lon)
    }
}