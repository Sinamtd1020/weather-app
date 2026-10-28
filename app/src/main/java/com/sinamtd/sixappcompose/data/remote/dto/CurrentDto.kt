package com.sinamtd.sixappcompose.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentDto(
    @SerializedName("time")val time: String,
    @SerializedName( "interval") val  interval: Int,
    @SerializedName("temperature_2m")val temperature_2m: Double,
    @SerializedName("relative_humidity_2m")val relative_humidity_2m: Int,
    @SerializedName("apparent_temperature")val apparent_temperature: Double,
    @SerializedName("weather_code")val weather_code: Int,
    @SerializedName("is_day")val is_day: Int,
    @SerializedName("wind_speed_10m") val wind_speed_10m: Double,
    @SerializedName("surface_pressure") val surface_pressure: Double?=null,
    @SerializedName("uv_index") val uv_index: Double?=null,
    @SerializedName("precipitation")val precipitation: Double
)