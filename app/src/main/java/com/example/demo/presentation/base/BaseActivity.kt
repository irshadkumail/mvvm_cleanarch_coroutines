package com.example.demo.presentation.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModel
import com.example.demo.presentation.utils.Dimens
import com.example.demo.presentation.utils.LocalDimens
import com.google.accompanist.appcompattheme.AppCompatTheme

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompatTheme {
                CompositionLocalProvider(
                    LocalDimens provides Dimens()
                ) {
                    ScreenView()
                }
            }
        }
    }

    @Composable
    abstract fun ScreenView()


}
