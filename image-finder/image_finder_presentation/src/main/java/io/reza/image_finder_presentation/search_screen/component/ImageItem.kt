package io.reza.image_finder_presentation.search_screen.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.reza.image_finder_domain.model.ImageData

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageItem(
    imageData: ImageData,
    onItemClicked: (Int) -> Unit
) {

    Card(
        modifier = Modifier
            .size(width = 100.dp, height = 180.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp,
        onClick = {
            onItemClicked(imageData.remoteId)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
        ) {
            AsyncImage(
                model = imageData.previewURL,
                contentDescription = imageData.user,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 18.dp,
                            bottomEnd = 18.dp
                        )
                    )
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = imageData.user,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.Transparent)
                        )
                    )
                    .padding(4.dp),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.background
            )
            Text(
                text = "Tags: ${imageData.tags}",
                style = MaterialTheme.typography.body2.copy(
                    fontSize = 12.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }

}