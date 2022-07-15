//package io.reza.image_finder_domain.use_case
//
//import io.reza.image_finder_domain.model.ImageData
//import io.reza.image_finder_domain.repository.ImageFinderRepository
//
//class SearchImage(
//    private val repository: ImageFinderRepository
//) {
//
//    suspend operator fun invoke(
//        query: String,
//        page: Int,
//        perPage: Int = 20,
//        imageType: String = "photo"
//    ): Result<List<ImageData>> {
//        if (query.isBlank()) {
//            return Result.success(emptyList())
//        }
//        return repository.searchImages(query.trim(), page, pageSize)
//    }
//
//}