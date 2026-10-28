package com.sinamtd.sixappcompose.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinamtd.sixappcompose.data.datastore.UserPreferencesRepository
import com.sinamtd.sixappcompose.data.remote.dto.GeocodingResult

import com.sinamtd.sixappcompose.domain.usecase.GetAirQualityUseCase
import com.sinamtd.sixappcompose.domain.usecase.GetCurrentWeatherUseCase
import com.sinamtd.sixappcompose.domain.usecase.SearchCityUseCase
import com.sinamtd.sixappcompose.presentation.weather.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor
    ( private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
      private val searchCityUseCase: SearchCityUseCase,
      private val userPreferencesRepository: UserPreferencesRepository,
      private val getAirQualityUseCase: GetAirQualityUseCase
): ViewModel(){
        private val _state= MutableStateFlow((WeatherState()))
        val state : StateFlow<WeatherState> = _state.asStateFlow()

    private val _favorites = MutableStateFlow<List<String>>(emptyList())
    val favorites = _favorites.asStateFlow()



       // جستجوی شهر با قابلیت debounce (بهینه برای پرفورمنس)

       init {
        viewModelScope.launch {
            val lastCity=userPreferencesRepository.lastCityFlow.first()
            getWeather(lastCity)

        }
    }
       private val searchQueryFlow = MutableStateFlow("")

       init {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(300) // ۳۰۰ میلیثانیه تأخیر برای جلوگیری از درخواستهای اضافی
                .filter { it.length >= 1 } // فقط بعد از ۲ کاراکتر شروع به جستجو کن
                .distinctUntilChanged() // اگر مقدار تکراری بود، درخواست نده
                .collect { query ->
                    searchCity(query)
                }
        }
    }










    fun getWeather(city: String){
        viewModelScope.launch {
            _state.value= WeatherState(isLoading = true, error = "")

        val result=
        getCurrentWeatherUseCase(city)

        result.onSuccess {weather ->
            _state.value = _state.value.copy(
                isLoading = false,
                weather = weather,
                selectedCity = city,
                searchResult = emptyList(),
                searchQuery = ""
                )


            val lat=weather.latitude
            val lan=weather.longitude
            getAirQuality(lat,lan)
            userPreferencesRepository.saveLastCity(city)

        }
            .onFailure { exception ->
                _state.value= WeatherState(error = exception.message?:"Unknown Error")
                _state.value=_state.value.copy(
                    isLoading = false,
                    error = exception.message?:"Unknown"
                )
            }
        }
    }
    private fun getAirQuality(lat: Double, lon: Double) {
        viewModelScope.launch {

            val result = getAirQualityUseCase(lat, lon)
            result.onSuccess { airQuality ->
                _state.value = _state.value.copy(airQuality = airQuality)
            }.onFailure { exception ->
                Log.e("AirQuality", "خطا در دریافت کیفیت هوا: ${exception.message}")
            }
        }
    }
    fun updateSearchQuery(query: String) {
        _state.value = _state.value.copy(
            searchQuery = query
        )
        searchQueryFlow.value = query
    }
    private fun searchCity(query: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSearching = true)
            val result = searchCityUseCase(query)
            result.onSuccess { cities ->
                _state.value = _state.value.copy(
                    isSearching = false,
                    searchResult = cities
                )
            }.onFailure { exception ->
                _state.value = _state.value.copy(
                    isSearching = false,
                    error = exception.message ?: "خطا در جستجو"
                )
            }
        }
    }

    fun selectCity(city: GeocodingResult) {

        _state.value=_state.value.copy(selectedCity = city.name,selectedCityCountry = city.country)
        getWeather(city.name) // city.name همان نام انگلیسی است
    }

    fun clearSearch() {
        _state.value = _state.value.copy(
            searchQuery = "",
            searchResult = emptyList()
        )
    }

}
