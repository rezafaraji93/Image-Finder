package io.reza.image_finder_data.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reza.image_finder_data.local.ImageFinderDatabase
import io.reza.image_finder_data.remote.PixabayApi
import io.reza.image_finder_data.repository.ImageFinderRepositoryImpl
import io.reza.image_finder_data.repository.PrefsStoreImpl
import io.reza.image_finder_domain.repository.ImageFinderRepository
import io.reza.image_finder_domain.repository.PrefsStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageFinderDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodApi(client: OkHttpClient): PixabayApi {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(PixabayApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideImageFinderDatabase(app: Application): ImageFinderDatabase {
        return Room.databaseBuilder(
            app,
            ImageFinderDatabase::class.java,
            ImageFinderDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatastore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(PrefsStore.DATASTORE_NAME)
            }
        )

    @Provides
    @Singleton
    fun providePrefsStore(dataStore: DataStore<Preferences>): PrefsStore {
        return PrefsStoreImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideImageFinderRepository(
        api: PixabayApi,
        db: ImageFinderDatabase,
        prefsStore: PrefsStore
    ): ImageFinderRepository {
        return ImageFinderRepositoryImpl(api, db, prefsStore)
    }

}