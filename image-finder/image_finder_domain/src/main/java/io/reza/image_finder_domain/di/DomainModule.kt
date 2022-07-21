package io.reza.image_finder_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reza.image_finder_domain.repository.ImageFinderRepository
import io.reza.image_finder_domain.use_case.GetImageDetailsUseCase
import io.reza.image_finder_domain.use_case.SearchImageUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetImageDetailUseCase(repository: ImageFinderRepository): GetImageDetailsUseCase {
        return GetImageDetailsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchImageUseCase(repository: ImageFinderRepository): SearchImageUseCase {
        return SearchImageUseCase(repository)
    }

}