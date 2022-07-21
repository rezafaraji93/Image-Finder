package io.reza.image_finder_data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import io.reza.image_finder_domain.repository.PrefsStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

private const val TEST_DATASTORE_NAME: String = "test_datastore"

private const val TEST_API_KEY = "TEST_API_KEY"


class PrefsStoreImplTest {
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testScope = TestScope()

    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = {
                testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME)
            }
        )

    private val repository: PrefsStore =
        PrefsStoreImpl(testDataStore)


    @Test
    fun saveApiKeyAndFetch() = runTest {
        repository.saveApiKey(TEST_API_KEY)

        val apiKey = repository.apiKey.first()

        assertThat(apiKey).isEqualTo(TEST_API_KEY)

    }
}