package io.reza.image_finder_data.mapper

import com.google.common.truth.Truth.assertThat
import io.reza.image_finder_data.remote.dto.ImageDataDto
import io.reza.image_finder_domain.model.ImageData

import org.junit.Test

class ImageDataMapperKtTest {

    @Test
    fun toImageData() {
        val dto = ImageDataDto(
            id = 1,
            pageURL = "pageUrl",
            type = "type",
            tags = "android, unit, test",
            previewURL = "previewUrl",
            previewWidth = 100,
            previewHeight = 100,
            webformatHeight = 100,
            webformatURL = "webFormatUrl",
            webformatWidth = 100,
            largeImageURL = "largeImageUrl",
            imageWidth = 100,
            imageHeight = 100,
            imageSize = 100,
            views = 150,
            downloads = 250,
            collections = 1,
            likes = 1500,
            comments = 2000,
            user_id = 1000,
            user = "user",
            userImageURL = "userImageUrl"
        )

        val expected = ImageData(
            id = 0,
            remoteId = 1,
            query = "",
            pageURL = "pageUrl",
            tags = "android, unit, test",
            previewURL = "previewUrl",
            previewHeight = 100,
            previewWidth = 100,
            largeImageURL = "largeImageUrl",
            largeImageWidth = 100,
            largeImageHeight = 100,
            downloads = 250,
            likes = 1500,
            comments = 2000,
            userId = 1000,
            user = "user",
            userImageURL = "userImageUrl"

        )

        assertThat(dto.toImageData()).isEqualTo(expected)

    }
}