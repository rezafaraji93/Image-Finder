package io.reza.image_finder_presentation.search_screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.reza.core_ui.ErrorState
import io.reza.core_ui.HintColor
import io.reza.image_finder_domain.model.ImageData
import io.reza.image_finder_presentation.search_screen.component.ImageItem
import io.reza.image_finder_presentation.search_screen.component.ImagePlaceHolder
import io.reza.image_finder_presentation.search_screen.component.SearchBar


@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    onNavigateToDetailScreen: (Int) -> Unit,
    viewModel: SearchScreenViewModel
) {

    SearchScreenLoader(
        searchScreenViewModel = viewModel,
        navigateToUserDetail = onNavigateToDetailScreen
    )

}

@Composable
private fun SearchScreenLoader(
    searchScreenViewModel: SearchScreenViewModel,
    navigateToUserDetail: (imageId: Int) -> Unit
) {
    val viewState by searchScreenViewModel.uiState.collectAsState()
    val actions = SearchScreenActions(
        onInputTextChanged = searchScreenViewModel::onInputTextChanged,
        focusSearchBar = searchScreenViewModel::updateSearchBarState,
        resetToInitialState = searchScreenViewModel::resetToInitialState,
        openUserDetail = navigateToUserDetail
    )
    SearchScreenScaffold(viewState = viewState, actions = actions)
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun SearchScreenScaffold(viewState: SearchScreenState, actions: SearchScreenActions) {
    Scaffold {
        val lazyPagingItems = viewState.pagingData?.collectAsLazyPagingItems()
        MainContent(
            inputText = viewState.inputText,
            lazyPagingItems = lazyPagingItems,
            actions = actions,
            isSearchBarHasFocus = viewState.isSearchBarFocused
        )
        SnackbarHost(lazyPagingItems)
    }
    if (viewState.lastSearchedQuery.isNotEmpty()) {
        BackHandler {
            actions.resetToInitialState()
        }
    }
}

@Composable
private fun MainContent(
    inputText: String,
    lazyPagingItems: LazyPagingItems<ImageData>?,
    actions: SearchScreenActions,
    isSearchBarHasFocus: Boolean
) {
    val searchBarHeight = 54.dp
    val searchBarHeightWithPadding = searchBarHeight + 16.dp
    val refreshLoadState = lazyPagingItems?.loadState?.refresh

    if (lazyPagingItems == null) {
        InitialState(startSearch = { actions.focusSearchBar(true) })
    } else if (refreshLoadState is LoadState.Loading) {
        ImagesListLoadingState(modifier = Modifier.padding(top = searchBarHeightWithPadding))
    } else if (refreshLoadState is LoadState.Error && lazyPagingItems.itemCount == 0) {
        ErrorState(onRetry = { lazyPagingItems.retry() })
    } else if (lazyPagingItems.loadState.append.endOfPaginationReached && lazyPagingItems.itemCount == 0) {
        NoImagesFoundState()
    } else {
        val lazyGridState = rememberLazyGridState()
        KeyboardManager(lazyGridState)
        ImagesList(
            lazyGridState = lazyGridState,
            contentPadding = PaddingValues(top = searchBarHeightWithPadding, bottom = 70.dp),
            items = lazyPagingItems,
            actions = actions
        )
    }

    SearchBar(
        modifier = Modifier
            .padding(16.dp)
            .height(searchBarHeight),
        inputText = inputText,
        onKeyboardStateChanged = { isOpen -> if (!isOpen) actions.focusSearchBar(false) },
        requestFocus = isSearchBarHasFocus,
        onTextChanged = actions.onInputTextChanged
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun KeyboardManager(lazyGridState: LazyGridState) {
    val keyboardController = LocalSoftwareKeyboardController.current
    if (lazyGridState.isScrollInProgress) {
        LaunchedEffect(lazyGridState.isScrollInProgress) {
            keyboardController?.hide()
        }
    }
}

@Composable
private fun SnackbarHost(lazyPagingItems: LazyPagingItems<ImageData>?) {
    val state = remember { SnackbarHostState() }
    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = state,
            snackbar = { snackbarData ->
                Snackbar(
                    modifier = Modifier
                        .padding(16.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.error,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    action = {
                        TextButton(
                            onClick = {
                                lazyPagingItems?.retry()
                                state.currentSnackbarData?.dismiss()
                            }
                        ) {
                            Text(
                                text = snackbarData.actionLabel ?: "",
                                color = Red
                            )
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    elevation = 0.dp,
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.onError,
                ) {
                    Text(text = snackbarData.message)
                }
            }
        )
    }
    val pagingAppendLoadState = lazyPagingItems?.loadState?.append
    if (pagingAppendLoadState is LoadState.Error) {
        val errorText = "snackbar error"
        val retryText = "retry"
        LaunchedEffect(pagingAppendLoadState.error) {
            state.showSnackbar(
                message = errorText,
                actionLabel = retryText,
                duration = SnackbarDuration.Indefinite
            )
        }
    }
}

@Composable
private fun NoImagesFoundState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = io.reza.core.R.drawable.logo),
            contentDescription = "noImagesFoundPlaceHolderImage",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.surface)
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(io.reza.core.R.string.noImagesFound),
            fontSize = 12.sp,
            color = HintColor
        )
    }
}

@Composable
private fun InitialState(startSearch: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = io.reza.core.R.drawable.logo),
            contentDescription = "initialStatePlaceHolderImage",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.surface)
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(io.reza.core.R.string.search), color = HintColor)
            Icon(
                modifier = Modifier.padding(start = 8.dp),
                imageVector = Icons.Rounded.ArrowForward,
                tint = HintColor,
                contentDescription = "arrowIcon"
            )
            FloatingActionButton(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .testTag("searchButton"),
                shape = RoundedCornerShape(10.dp),
                contentColor = MaterialTheme.colors.onBackground,
                onClick = { startSearch() }
            ) {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = "searchIcon")
            }
        }
    }
}

@Composable
private fun ImagesList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    lazyGridState: LazyGridState = rememberLazyGridState(),
    items: LazyPagingItems<ImageData>,
    actions: SearchScreenActions
) {
    val configuration = LocalConfiguration.current

    val cellsCount = remember(configuration.orientation) {
        when (configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 2
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier.testTag("imagesList"),
            state = lazyGridState,
            contentPadding = contentPadding,
            columns = GridCells.Fixed(cellsCount)
        ) {

            if (items.itemCount == 0) {
                items(20) {
                    ImagePlaceHolder()
                }
            } else {
                items(items.itemCount) { position ->
                    ImageItem(
                        imageData = items[position] ?: ImageData(),
                        onItemClicked = { actions.openUserDetail(it) })
                }
            }
        }
    }
}

@Composable
private fun ImagesListLoadingState(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        repeat(20) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ImagePlaceHolder()
                ImagePlaceHolder()
            }
        }

    }
}