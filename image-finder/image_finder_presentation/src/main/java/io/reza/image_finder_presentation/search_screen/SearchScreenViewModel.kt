package io.reza.image_finder_presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reza.image_finder_domain.model.ImageData
import io.reza.image_finder_domain.use_case.SearchImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchImageUseCase: SearchImageUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(SearchScreenState())
    val uiState = viewModelState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = viewModelState.value
    )

    private var searchDebouncerJob: Job = Job()
    private var currentQuery = ""
    private val ioDispatcher = Dispatchers.IO

    init {
        search("fruits")
    }

    fun onInputTextChanged(text: String) {
        viewModelState.update {
            it.copy(inputText = text)
        }
        search(text)
    }

    private fun search(query: String) {
        searchDebouncerJob.cancel()
        currentQuery = query

        if (query.isNotEmpty()) {
            searchDebouncerJob = viewModelScope.launch(ioDispatcher) {
                delay(timeMillis = 700)
                if (currentQuery != viewModelState.value.lastSearchedQuery) {
                    viewModelState.update { state ->
                        state.copy(lastSearchedQuery = query, pagingData = getPagingDataFlow(query))
                    }
                }
            }
        } else {
            viewModelState.update { state ->
                state.copy(lastSearchedQuery = "", pagingData = null)
            }
        }
    }

    private fun getPagingDataFlow(searchQuery: String): Flow<PagingData<ImageData>> =
        searchImageUseCase(searchQuery).cachedIn(viewModelScope)

    fun updateSearchBarState(isFocused: Boolean) {
        viewModelState.update {
            it.copy(isSearchBarFocused = isFocused)
        }
    }

    fun resetToInitialState() {
        viewModelState.update {
            it.copy(
                inputText = "",
                lastSearchedQuery = "",
                pagingData = null,
                isSearchBarFocused = false
            )
        }
    }

}