package io.reza.image_finder_presentation.image_detail_screen

import io.reza.image_finder_domain.model.ImageData

data class ImageDetailScreenState(
    val imageData: ImageData = ImageData(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isLoadingImage: Boolean = true
)
