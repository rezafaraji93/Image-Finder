package io.reza.image_finder_domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_data")
data class ImageData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val remoteId: Int,
    val query: String = "",
    val pageURL: String,
    val tags: String,
    val previewURL: String,
    val previewWidth: Int,
    val previewHeight: Int,
    val largeImageURL: String,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val userId: Int,
    val user: String,
    val userImageURL: String
)
