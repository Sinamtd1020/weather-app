package com.sinamtd.sixappcompose.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirQualityResponse(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("timezone") val timezone: String,
    @SerialName("current") val current: AirQualityCurrent? = null
)

@Serializable
data class AirQualityCurrent(
    @SerialName("time") val time: String,
    @SerialName("us_aqi") val usAqi: Int? = null,
    @SerialName("european_aqi") val europeanAqi: Int? = null,
    @SerialName("pm10") val pm10: Double? = null,
    @SerialName("pm2_5") val pm25: Double? = null,
    @SerialName("carbon_monoxide") val carbonMonoxide: Double? = null,
    @SerialName("nitrogen_dioxide") val nitrogenDioxide: Double? = null,
    @SerialName("sulphur_dioxide") val sulphurDioxide: Double? = null,
    @SerialName("ozone") val ozone: Double? = null
)