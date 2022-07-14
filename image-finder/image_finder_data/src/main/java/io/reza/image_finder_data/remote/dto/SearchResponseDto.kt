package io.reza.image_finder_data.remote.dto

data class SearchResponseDto(
    val total: Int,
    val totalHits: Int,
    val hits: List<Hit>
)