package io.reza.image_finder_presentation.image_detail_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import io.reza.image_finder_presentation.image_detail_screen.component.ImageDetailTopBar
import io.reza.image_finder_presentation.image_detail_screen.component.ImageInfoBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageDetailScreen(
    onBackPressed: () -> Unit,
    onOpenUrl: (url: String) -> Unit,
    viewModel: ImageDetailScreenViewModel = hiltViewModel()
) {

    val state = viewModel.state

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            if (state.isError) {
                Text(
                    text = stringResource(id = io.reza.core_ui.R.string.detailNotFound),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    stickyHeader {
                        ImageDetailTopBar(
                            onBackPressed = onBackPressed,
                            onOpenUrl = {
                                onOpenUrl(state.imageData.pageURL)
                            }
                        )
                    }
                    item {
                        AsyncImage(
                            model = state.imageData.largeImageURL,
                            contentDescription = state.imageData.user,
                            onLoading = {
                                viewModel.toggleImageLoading(true)
                            },
                            onSuccess = {
                                viewModel.toggleImageLoading(false)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(
                                    minHeight = 250.dp
                                )
                                .placeholder(
                                    visible = state.isLoadingImage,
                                    highlight = PlaceholderHighlight.shimmer()
                                )
                        )

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 48.dp)
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = 5.dp,
                        ) {

                            Column {

                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    AsyncImage(
                                        model = state.imageData.userImageURL,
                                        contentDescription = state.imageData.user,
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(56.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = state.imageData.user,
                                        style = MaterialTheme.typography.body1,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = stringResource(
                                        id = io.reza.core_ui.R.string.tags,
                                        state.imageData.tags
                                    ),
                                    style = MaterialTheme.typography.body2,
                                    modifier = Modifier.padding(start = 8.dp)
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    ImageInfoBox(
                                        icon = Icons.Outlined.Favorite,
                                        amount = state.imageData.likes
                                    )
                                    ImageInfoBox(
                                        icon = ImageVector.vectorResource(id = io.reza.core_ui.R.drawable.comments),
                                        amount = state.imageData.comments
                                    )
                                    ImageInfoBox(
                                        icon = ImageVector.vectorResource(id = io.reza.core_ui.R.drawable.downloads),
                                        amount = state.imageData.downloads
                                    )
                                }

                            }
                        }

                    }
                }
            }
        }

    }

}