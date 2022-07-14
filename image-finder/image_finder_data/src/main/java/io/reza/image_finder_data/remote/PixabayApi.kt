package io.reza.image_finder_data.remote

import io.reza.image_finder_data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api/")
    suspend fun searchImage(
        @Query("key") key: String,
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("image_type") imageType: String
    ): SearchResponseDto

    companion object {
        const val BASE_URL = "https://pixabay.com/"
    }

}