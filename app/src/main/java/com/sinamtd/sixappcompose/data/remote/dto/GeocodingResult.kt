package com.sinamtd.sixappcompose.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponse(
    @SerializedName("results")val results: List<GeocodingResult>? = emptyList()
)

