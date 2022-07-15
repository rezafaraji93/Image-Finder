package io.reza.image_finder_presentation.search_screen

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel()
) {

    val paging = viewModel.pagingData?.collectAsLazyPagingItems()

    LaunchedEffect(key1 = paging) {

        paging?.let { it ->
            if (it.itemCount > 0) {
                (0..it.itemCount).forEach { pos ->
                    Log.e("TAG", "SearchScreen: ${it[pos]?.remoteId}", )
                }
            }
        }
    }

    LazyColumn {
        paging?.let {
            items(it) { items ->
                AsyncImage(
                    model = items?.previewURL,
                    contentDescription = items?.user
                )
            }
            paging.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }
                    loadState.append is LoadState.Loading -> {
                    }
                    loadState.refresh is LoadState.Error -> {
                    }
                    loadState.append is LoadState.Error -> {
                    }
                }

            }
        }
    }
}

