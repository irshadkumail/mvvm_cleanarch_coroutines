package com.example.demo.presentation.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(

    //Divider values
    val divider: Dp = 1.dp,
    val dividerSmall: Dp = 2.dp,
    val dividerMedium: Dp = 4.dp,

    //Padding values
    val paddingExtraExtraSmall: Dp = 4.dp,
    val paddingExtraSmall: Dp = 8.dp,
    val paddingSmall: Dp = 12.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 20.dp,
    val paddingExtraLarge: Dp = 24.dp,
    val paddingNetworkImageHorizontal: Dp = 40.dp,
    val paddingNetworkImageVertical: Dp = 24.dp,

    //Icon sizes
    val iconExtraSmall: Dp = 12.dp,
    val iconSmall: Dp = 16.dp,
    val iconMedium: Dp = 20.dp,
    val iconLarge: Dp = 24.dp,
    val iconExtraLarge: Dp = 32.dp,
    )


val LocalDimens = staticCompositionLocalOf { Dimens() }

val MaterialTheme.dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current