package com.example.demo.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.domain.GetAllDomains
import com.example.demo.domain.GetAverageLatency
import com.example.demo.domain.models.AverageLatencyModel
import com.example.demo.domain.models.DomainModel
import com.example.demo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

sealed class HomeState {
    object Loading : HomeState()
    data class DomainReceivedState(val domains: List<DomainModel> = emptyList()) :
        HomeState()

    data class Error(val exception: Exception) : HomeState()
}

sealed class HomeIntent {
    object GetDomains : HomeIntent()
    data class FetchLatency(val domainModel: DomainModel) : HomeIntent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val getAllDomains: GetAllDomains,
    private val getAverageLatency: GetAverageLatency
) : BaseViewModel() {

    var state by mutableStateOf<HomeState>(HomeState.Loading)
        private set

    val list = mutableStateListOf<AverageLatencyModel>()

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.GetDomains -> {
                fetchDomains()
            }
            is HomeIntent.FetchLatency -> {
                fetchLatency(intent.domainModel)
            }
        }
    }

    private fun fetchDomains() = safeApiCall(onExecute = {
        state = HomeState.Loading
        val domains = getAllDomains.execute(false)
        state = HomeState.DomainReceivedState(domains = domains)

        domains.map { handleIntent(HomeIntent.FetchLatency(it)) }
    }, onError = {
        state = HomeState.Error(it)
    })

    private fun fetchLatency(domainModel: DomainModel) = safeApiCall(
        onExecute = {
            val averageLatency = getAverageLatency.execute(domainModel)
            list.removeIf { it.domain == averageLatency.domain }
            list.add(averageLatency)
        }, onError = {
            //Ignore Error
        })

    fun getLatencyForDomain(domainUrl: String): String {
        val avgLatency = list.find { it.domain == domainUrl }

        return when {
            avgLatency == null -> "Loading..."
            avgLatency.isAvailable.not() -> "Unavailable"
            else -> "${avgLatency.latency}ms"
        }

    }

}