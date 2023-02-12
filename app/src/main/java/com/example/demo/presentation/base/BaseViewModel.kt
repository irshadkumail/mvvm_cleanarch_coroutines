package com.example.demo.presentation.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.internal.wait

abstract class BaseViewModel : ViewModel() {

    fun safeApiCall(onExecute: suspend () -> Unit, onError: (e: Exception) -> Unit = { }) {
        val job: Job = viewModelScope.launch {
            try {
                onExecute()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}