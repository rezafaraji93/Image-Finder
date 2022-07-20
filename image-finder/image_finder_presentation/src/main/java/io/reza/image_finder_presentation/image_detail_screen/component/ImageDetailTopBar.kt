package io.reza.image_finder_presentation.image_detail_screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import io.reza.core_ui.R

@Composable
fun ImageDetailTopBar(
    onBackPressed: () -> Unit,
    onOpenUrl: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        IconButton(
            onClick = onBackPressed,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "arrowBack")
        }

        IconButton(
            onClick = onOpenUrl,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.open_in_browser),
                contentDescription = "openInWeb"
            )
        }
    }

}