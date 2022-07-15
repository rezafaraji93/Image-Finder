package io.reza.image_finder_presentation.search_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reza.image_finder_domain.model.ImageData
import io.reza.image_finder_domain.repository.ImageFinderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: ImageFinderRepository
) : ViewModel() {

    var pagingData: Flow<PagingData<ImageData>>? = null


    init {
        Log.e("TAG", ": init viewModel" )
        viewModelScope.launch {
            pagingData = repository.searchImages("fruit").cachedIn(viewModelScope)
        }
    }

}