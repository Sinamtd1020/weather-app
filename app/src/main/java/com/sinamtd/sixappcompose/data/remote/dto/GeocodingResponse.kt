package com.sinamtd.sixappcompose.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResult(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?=null

)
