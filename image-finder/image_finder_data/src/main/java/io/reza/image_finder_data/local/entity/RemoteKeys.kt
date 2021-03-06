package io.reza.image_finder_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val imageId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)