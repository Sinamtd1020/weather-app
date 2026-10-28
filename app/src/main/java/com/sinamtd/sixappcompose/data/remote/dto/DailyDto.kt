package com.sinamtd.sixappcompose.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.sql.Time

@Serializable
data class DailyDto(
    @SerializedName("time")val time: List<String>,
    @SerializedName("weather_code")val weather_code: List<Int>,
    @SerializedName("precipitation_probability_mean")val precipitation_probability_mean: List<Int>,
    @SerializedName("temperature_2m_max") val temperature_2m_max: List<Double>,
    @SerializedName("temperature_2m_mean") val temperature_2m_mean: List<Double>,
    @SerializedName("temperature_2m_min") val temperature_2m_min: List<Double>,
    @SerializedName("precipitation_sum")val precipitation_sum: List<Double>?=null,
    @SerializedName("wind_speed_10m_max")val wind_speed_10m_max: List<Double>?=null,
    )