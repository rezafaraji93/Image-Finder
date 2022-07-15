package io.reza.image_finder_data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.reza.image_finder_domain.repository.PrefsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

class PrefsStoreImpl(
    private val dataStore: DataStore<Preferences>
) : PrefsStore {

    override val apiKey: Flow<String> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[PreferenceKey.API_KEY] ?: ""
            }


    override suspend fun saveApiKey(key: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.API_KEY] = key

        }
    }


    object PreferenceKey {
        val API_KEY = stringPreferencesKey("apiKey")
    }

}