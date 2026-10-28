package com.sinamtd.sixappcompose.domain.repository

import com.sinamtd.sixappcompose.data.remote.dto.GeocodingResult

interface GeocodingRepository {
    suspend fun searchCity(query: String): Result<List<GeocodingResult>>
}