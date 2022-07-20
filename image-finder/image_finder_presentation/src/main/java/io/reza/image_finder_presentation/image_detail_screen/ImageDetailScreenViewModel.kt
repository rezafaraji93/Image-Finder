package io.reza.image_finder_presentation.image_detail_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reza.image_finder_domain.use_case.GetImageDetailsUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailScreenViewModel @Inject constructor(
    private val getImageDetailsUseCase: GetImageDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(ImageDetailScreenState())
        private set

    private val imageId = savedStateHandle.get<Int>("id")

    init {
        if (imageId != null) {
            viewModelScope.launch {
                getImageDetailsUseCase(imageId).collectLatest { imageData ->
                    state = state.copy(
                        imageData = imageData,
                        isLoading = false
                    )
                }
            }
        } else {
            state = state.copy(
                isLoading = false,
                isError = true
            )
        }
    }

    fun toggleImageLoading(loading: Boolean) {
        state = state.copy(
            isLoadingImage = loading
        )
    }

}