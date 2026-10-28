package com.sinamtd.sixappcompose.data.remote.api

import com.sinamtd.sixappcompose.data.remote.dto.GeocodingResponse
import com.sinamtd.sixappcompose.data.remote.dto.GeocodingResult
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {
    @GET("search?format=json")
    suspend fun searchLocation(
        @Query("name") cityName: String
    ): GeocodingResponse
}