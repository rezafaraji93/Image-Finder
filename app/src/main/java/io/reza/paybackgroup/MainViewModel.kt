package io.reza.paybackgroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reza.image_finder_domain.repository.PrefsStore
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefsStore: PrefsStore
): ViewModel() {

    fun saveApiKey() {
        viewModelScope.launch {
            prefsStore.saveApiKey(PrefsStore.API_KEY)
        }
    }

}