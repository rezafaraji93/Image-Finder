package io.reza.image_finder_data.remote.dto

import com.squareup.moshi.Json

data class SearchResponseDto(
    @field:Json(name = "hits")
    val images: List<ImageDataDto>
)