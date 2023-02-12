package com.example.demo.domain

import android.content.Context
import com.example.demo.data.model.DomainResponseModel
import com.example.demo.di.DefaultDispatcherProvider
import com.example.demo.di.DispatcherProvider
import com.example.demo.domain.base.BaseUseCase
import com.example.demo.domain.models.DomainModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.lang.Exception
import javax.inject.Inject

class GetAllDomains @Inject constructor(
    @ApplicationContext applicationContext: Context,
    private val repository: DomainRepository,
    private val dispatcher: DispatcherProvider
) : BaseUseCase<Boolean, List<DomainModel>>(applicationContext) {


    override suspend fun execute(disableCache: Boolean): List<DomainModel> =
        withContext(dispatcher.io()) {
            try {
                delay(3000)
                val responseModels = repository.getDomains()
                repository.saveDomainsToCache(responseModels)

                responseModels.map { it.toBusinessModel() }
            } catch (ex: Exception) {
                if (disableCache.not())
                    repository.getLastSavedDomains().map { it.toBusinessModel() }
                else
                    throw ex
            }
        }

}