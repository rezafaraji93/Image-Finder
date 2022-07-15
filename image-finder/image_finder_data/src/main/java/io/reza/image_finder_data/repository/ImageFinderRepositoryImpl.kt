package io.reza.image_finder_data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.reza.image_finder_data.local.ImageFinderDatabase
import io.reza.image_finder_data.mediator.ImagesRemoteMediator
import io.reza.image_finder_data.remote.PixabayApi
import io.reza.image_finder_domain.model.ImageData
import io.reza.image_finder_domain.repository.ImageFinderRepository
import io.reza.image_finder_domain.repository.PrefsStore
import kotlinx.coroutines.flow.Flow

class ImageFinderRepositoryImpl(
    private val api: PixabayApi,
    private val db: ImageFinderDatabase,
    private val prefsStore: PrefsStore
) : ImageFinderRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun searchImages(
        query: String
    ): Flow<PagingData<ImageData>> {
        val pagingSourceFactory = { db.imageDataDao.searchImages(query.trim().lowercase()) }
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            remoteMediator = ImagesRemoteMediator(
                query = query,
                api = api,
                db = db,
                prefsStore = prefsStore
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


}