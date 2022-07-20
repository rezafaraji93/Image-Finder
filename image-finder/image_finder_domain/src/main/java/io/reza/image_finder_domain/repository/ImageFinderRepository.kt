package io.reza.image_finder_domain.repository

import androidx.paging.PagingData
import io.reza.image_finder_domain.model.ImageData
import kotlinx.coroutines.flow.Flow

interface ImageFinderRepository {

    fun searchImages(
        query: String,
    ): Flow<PagingData<ImageData>>

    fun getImageData(imageId: Int): Flow<ImageData>

}