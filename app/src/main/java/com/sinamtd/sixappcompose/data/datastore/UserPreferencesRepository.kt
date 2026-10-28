package com.sinamtd.sixappcompose.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {
    companion object{
        private val LAST_CITY_KEY = stringPreferencesKey("last_city")

    }
    val lastCityFlow: Flow<String> =context.dataStore.data
        .map { preferences ->
            preferences[LAST_CITY_KEY]?:"Tehran"
        }




    suspend fun saveLastCity(city: String){
        context.dataStore.edit { preferences ->
            preferences[LAST_CITY_KEY]=city
        }
    }


}