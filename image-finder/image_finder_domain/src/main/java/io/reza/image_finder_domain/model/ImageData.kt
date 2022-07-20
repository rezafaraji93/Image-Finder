package io.reza.image_finder_domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_data")
data class ImageData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val remoteId: Int = 0,
    val query: String = "",
    val pageURL: String = "",
    val tags: String = "",
    val previewURL: String = "",
    val previewWidth: Int = 0,
    val previewHeight: Int = 0,
    val largeImageURL: String = "",
    val largeImageHeight: Int = 0,
    val largeImageWidth: Int = 0,
    val downloads: Int = 0,
    val likes: Int = 0,
    val comments: Int = 0,
    val userId: Int = 0,
    val user: String = "",
    val userImageURL: String = ""
)
