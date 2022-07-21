package io.reza.image_finder_presentation.search_screen.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.reza.core.R
import io.reza.core_ui.DarkBlue
import io.reza.core_ui.HintColor
import io.reza.core_ui.isKeyboardOpen

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onTextChanged: (text: String) -> Unit = {},
    onKeyboardStateChanged: (isOpen: Boolean) -> Unit = {},
    inputText: String = "",
    requestFocus: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(percent = 50)
            )
            .border(
                width = 1.dp,
                color = DarkBlue,
                shape = RoundedCornerShape(percent = 50)
            )
            .testTag("searchBar"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }
        val isKeyboardOpen by isKeyboardOpen()

        onKeyboardStateChanged(isKeyboardOpen)

        if (isKeyboardOpen.not()) {
            LaunchedEffect(isKeyboardOpen) {
                focusManager.clearFocus()
            }
        }

        if (requestFocus) {
            LaunchedEffect(requestFocus) {
                focusRequester.requestFocus()
            }
        }

        TextFiled(
            focusRequester = focusRequester,
            inputText = inputText,
            onTextChanged = onTextChanged,
            closeKeyboard = { focusManager.clearFocus() }
        )

        CancelIcon(
            isInSearchState = isKeyboardOpen || requestFocus,
            onTextChanged = onTextChanged,
            focusManager = focusManager
        )
    }
}

@Composable
private fun RowScope.TextFiled(
    focusRequester: FocusRequester,
    inputText: String,
    onTextChanged: (text: String) -> Unit,
    closeKeyboard: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .weight(1f)
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused }
            .testTag("searchTextField"),
        value = inputText,
        onValueChange = {
            if (!it.contains("\n")) {
                onTextChanged(it)
            }
        },
        keyboardActions = KeyboardActions(onDone = { closeKeyboard() }),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = "searchIcon",
                tint = HintColor
            )
        },
        placeholder = {
            AnimatedVisibility(visible = isFocused.not()) {
                Text(text = stringResource(R.string.search), color = HintColor)
            }
        }
    )
}

@Composable
private fun RowScope.CancelIcon(
    isInSearchState: Boolean,
    onTextChanged: (text: String) -> Unit,
    focusManager: FocusManager
) {
    Box(
        modifier = Modifier
            .padding(end = 16.dp)
            .size(28.dp)
    ) {
        this@CancelIcon.AnimatedVisibility(
            visible = isInSearchState,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            IconButton(onClick = {
                onTextChanged("")
                focusManager.clearFocus()
            }) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = "clearIcon")
            }
        }
    }
}
