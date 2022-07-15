package io.reza.image_finder_data.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import io.reza.image_finder_data.local.ImageFinderDatabase
import io.reza.image_finder_data.local.entity.RemoteKeys
import io.reza.image_finder_data.mapper.toImageData
import io.reza.image_finder_data.remote.PixabayApi
import io.reza.image_finder_domain.model.ImageData
import io.reza.image_finder_domain.repository.PrefsStore
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class ImagesRemoteMediator(
    private val query: String,
    private val api: PixabayApi,
    private val db: ImageFinderDatabase,
    private val prefsStore: PrefsStore
) : RemoteMediator<Int, ImageData>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageData>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        val apiKey = prefsStore.apiKey.first()

        try {
            val apiResponse =
                api.searchImage(apiKey, query, page, state.config.pageSize, "photo")

            val images = apiResponse.images.map {
                it.toImageData().copy(
                    query = query
                )
            }

            val endOfPaginationReached = apiResponse.images.size < state.config.pageSize
            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao.clearRemoteKeys()
                    db.imageDataDao.clearAllImageData()
                }
                val prevKey = if (page == STARTING_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = images.map {
                    RemoteKeys(imageId = it.remoteId, prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeysDao.insertAll(keys)
                db.imageDataDao.insertAll(images)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ImageData>): RemoteKeys? {

        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { image ->
                db.remoteKeysDao.remoteKeysImageId(image.remoteId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ImageData>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { imageData ->
                db.remoteKeysDao.remoteKeysImageId(imageData.remoteId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ImageData>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { image ->
                db.remoteKeysDao.remoteKeysImageId(image.remoteId)
            }
        }
    }
}
