package com.sinamtd.sixappcompose.data.remote.dto

import  com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val hourly: HourlyWeather?=null,
    val current: CurrentDto?=null,
    val daily: DailyDto?=null
)