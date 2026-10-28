package com.sinamtd.sixappcompose.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeather(
    val time: List<String>,
    val weather_code: List<Int>,
    val temperature_2m: List<Double>,
    val relative_humidity_2m: List<Double>?=null,
    val uv_index: List<Double>?=null
)
