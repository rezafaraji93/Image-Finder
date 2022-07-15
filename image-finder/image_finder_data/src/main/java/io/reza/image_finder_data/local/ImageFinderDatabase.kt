package io.reza.image_finder_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.reza.image_finder_data.local.entity.RemoteKeys
import io.reza.image_finder_domain.model.ImageData

@Database(
    entities = [ImageData::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class ImageFinderDatabase : RoomDatabase() {

    abstract val imageDataDao: ImageDataDao
    abstract val remoteKeysDao: RemoteKeysDao

    companion object {
        const val DATABASE_NAME = "image_finder_db"
    }

}