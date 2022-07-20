package io.reza.image_finder_presentation.search_screen

import androidx.paging.PagingData
import io.reza.image_finder_domain.model.ImageData
import kotlinx.coroutines.flow.Flow

data class SearchScreenState(
    val inputText: String = "fruits",
    val lastSearchedQuery: String = "",
    val pagingData: Flow<PagingData<ImageData>>? = null,
    val isSearchBarFocused: Boolean = false
)
