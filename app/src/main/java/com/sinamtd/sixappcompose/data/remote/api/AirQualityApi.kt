package com.sinamtd.sixappcompose.data.remote.api


import com.sinamtd.sixappcompose.data.remote.dto.AirQualityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityApi {
    @GET("air-quality")
    suspend fun getAirQuality(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "us_aqi,european_aqi,pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,ozone",
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecastDays: Int = 1
    ): AirQualityResponse
}