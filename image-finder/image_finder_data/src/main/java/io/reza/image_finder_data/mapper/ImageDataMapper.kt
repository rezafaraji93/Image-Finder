package io.reza.image_finder_data.mapper

import io.reza.image_finder_data.remote.dto.ImageDataDto
import io.reza.image_finder_domain.model.ImageData

fun ImageDataDto.toImageData(): ImageData {
    return ImageData(
        remoteId = id,
        pageURL = pageURL,
        tags = tags,
        previewURL = previewURL,
        previewWidth = previewWidth,
        previewHeight = previewHeight,
        largeImageURL = largeImageURL,
        largeImageHeight = imageHeight,
        largeImageWidth = imageWidth,
        downloads = downloads,
        likes = likes,
        comments = comments,
        userId = user_id,
        user = user,
        userImageURL = userImageURL
    )
}