package io.reza.image_finder_domain.use_case

import io.reza.image_finder_domain.model.ImageData
import io.reza.image_finder_domain.repository.ImageFinderRepository
import kotlinx.coroutines.flow.Flow

class GetImageDetailsUseCase(
    private val repository: ImageFinderRepository
) {

    operator fun invoke(imageId: Int): Flow<ImageData> = repository.getImageData(imageId)

}