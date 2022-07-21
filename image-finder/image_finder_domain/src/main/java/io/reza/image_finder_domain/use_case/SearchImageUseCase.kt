package io.reza.image_finder_domain.use_case

import androidx.paging.PagingData
import io.reza.image_finder_domain.model.ImageData
import io.reza.image_finder_domain.repository.ImageFinderRepository
import kotlinx.coroutines.flow.Flow

class SearchImageUseCase(
    private val repository: ImageFinderRepository
) {

    operator fun invoke(
        query: String,
    ): Flow<PagingData<ImageData>> {
        val actualQuery = query.trim().lowercase()
        return repository.searchImages(actualQuery)
    }

}