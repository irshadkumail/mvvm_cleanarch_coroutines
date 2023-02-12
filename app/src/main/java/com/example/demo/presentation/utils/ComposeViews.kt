package com.example.demo.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


/**
 * A basic screen ui composable that encapsulates the [Toolbar] and [ScreenBody] together.
 */
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    titleView: @Composable () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(horizontal = MaterialTheme.dimens.paddingSmall),
    content: @Composable ColumnScope.() -> Unit
) {

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = titleView,
        )
        ScreenBody(contentPadding = contentPadding, modifier = modifier) {
            content()
        }
    }
}


/**
 * Generates a column composable, that adds 12.dp screen horizontal padding,
 * which the standard across the app.
 */
@Composable
fun ScreenBody(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = MaterialTheme.dimens.paddingSmall),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
            .fillMaxWidth()
            .background(grey100)
            .padding(contentPadding)
    ) {
        content()
    }
}