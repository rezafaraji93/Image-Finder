package io.reza.image_finder_data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reza.image_finder_domain.model.ImageData

@Dao
interface ImageDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(imageData: List<ImageData>)

    @Query("SELECT * FROM image_data WHERE `query` LIKE :queryString")
    fun searchImages(queryString: String): PagingSource<Int, ImageData>

    @Query("DELETE FROM image_data")
    fun clearAllImageData()

}