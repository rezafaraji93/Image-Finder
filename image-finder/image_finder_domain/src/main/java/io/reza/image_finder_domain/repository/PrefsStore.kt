package io.reza.image_finder_domain.repository

import kotlinx.coroutines.flow.Flow

interface PrefsStore {

    val apiKey: Flow<String>

    suspend fun saveApiKey(key: String)

    companion object {
        const val DATASTORE_NAME = "imageFinderDs"
        const val API_KEY = "28623257-a1f4e5ff7f8aaec192bff3c4d"
    }

}