package com.example.demo.domain

import android.content.Context
import android.util.Log
import com.example.demo.di.DefaultDispatcherProvider
import com.example.demo.di.DispatcherProvider
import com.example.demo.domain.base.BaseUseCase
import com.example.demo.domain.models.AverageLatencyModel
import com.example.demo.domain.models.DomainModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GetAverageLatency @Inject constructor(
    @ApplicationContext applicationContext: Context,
    private val repository: DomainRepository,
    private val dispatcher: DispatcherProvider
) : BaseUseCase<DomainModel, AverageLatencyModel>(applicationContext) {

    override suspend fun execute(request: DomainModel): AverageLatencyModel =
        withContext(dispatcher.io()) {
          //  Log.d(javaClass.name, "Pinging ${request.domainUrl}")
            var latency = 0L
            try {
                repeat(MAX_ATTEMPTS) {
                    val response = repository.pingDomain(request.domainUrl)
                    latency += response.receivedResponseAtMillis - response.sentRequestAtMillis
                }
             //   Log.d(javaClass.name, "Ping Success ${request.domainUrl}")

                AverageLatencyModel(
                    request.domainUrl,
                    latency / MAX_ATTEMPTS,
                    true
                )
            } catch (ex: Exception) {
                AverageLatencyModel(request.domainUrl, 0, isAvailable = false)
            }
        }

    companion object {
        const val MAX_ATTEMPTS = 5
    }

}