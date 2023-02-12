package com.example.demo.domain.base

import android.app.Application
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

abstract class BaseUseCase<in Request, out Response>(protected val applicationContext: Context) {

    abstract suspend fun execute(request: Request): Response

}