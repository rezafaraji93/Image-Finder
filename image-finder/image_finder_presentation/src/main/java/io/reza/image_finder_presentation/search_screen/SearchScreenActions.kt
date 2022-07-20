package io.reza.image_finder_presentation.search_screen

data class SearchScreenActions(
    val onInputTextChanged: (query: String) -> Unit = {},
    val focusSearchBar: (focus: Boolean) -> Unit = {},
    val resetToInitialState: () -> Unit = {},
    val openUserDetail: (imageId: Int) -> Unit = {}
)