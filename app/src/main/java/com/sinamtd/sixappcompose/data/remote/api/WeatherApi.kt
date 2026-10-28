package com.sinamtd.sixappcompose.data.remote.api

import com.sinamtd.sixappcompose.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi{
    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String="temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,is_day,wind_speed_10m,precipitation,uv_index,surface_pressure",
        @Query("daily") daily : String="weather_code,temperature_2m_max,temperature_2m_min,temperature_2m_mean,precipitation_probability_mean,precipitation_sum,wind_speed_10m_max",
        @Query("timezone") timezone: String="auto",
        @Query("hourly") hourly: String="temperature_2m,weather_code,uv_index,relative_humidity_2m",
        @Query("forecast_days") forecastDays : Int=7
    ): WeatherDto
}