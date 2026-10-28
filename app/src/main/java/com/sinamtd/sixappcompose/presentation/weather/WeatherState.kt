package com.sinamtd.sixappcompose.presentation.weather

import com.sinamtd.sixappcompose.data.remote.dto.AirQualityResponse
import com.sinamtd.sixappcompose.data.remote.dto.GeocodingResult
import com.sinamtd.sixappcompose.data.remote.dto.WeatherDto

data class WeatherState(
    val isLoading: Boolean=false,
    val searchQuery: String="",
    val airQuality: AirQualityResponse?=null,
    val isSearching: Boolean=false,
    val selectedCity: String?=null,
    val selectedCityEnglish: String?=null,
    val selectedCityCountry: String?=null,
    val searchResult: List<GeocodingResult> = emptyList(),
    val weather: WeatherDto?=null,
    val error: String=""
    )