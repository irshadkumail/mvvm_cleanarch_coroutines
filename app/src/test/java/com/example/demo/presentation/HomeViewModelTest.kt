package com.example.demo.presentation

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.demo.CoroutineTestRule
import com.example.demo.data.model.DomainResponseModel
import com.example.demo.domain.GetAllDomains
import com.example.demo.domain.GetAverageLatency
import com.example.demo.domain.models.AverageLatencyModel
import com.example.demo.domain.models.DomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import kotlin.Exception

class HomeViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var getAllDomains: GetAllDomains

    @MockK
    lateinit var getAverageLatency: GetAverageLatency

    @Test
    fun testFetchDomainsSuccess() = runTest {
        coEvery { getAllDomains.execute(any()) }.returns(
            listOf(
                DomainModel(name = "Google", domainUrl = "google.com", domainIcon = ""),
                DomainModel(name = "Yahoo", domainUrl = "yahoo.com", domainIcon = "")
            )
        )

        coEvery { getAverageLatency.execute(any()) }.returns(
            AverageLatencyModel(domain = "google.com", latency = 100, isAvailable = false),
        )

        val viewModel = HomeViewModel(context, getAllDomains, getAverageLatency)
        assertEquals(HomeState.Loading.javaClass, viewModel.state.javaClass)
        viewModel.handleIntent(HomeIntent.GetDomains)
        assertEquals(HomeState.DomainReceivedState().javaClass, viewModel.state.javaClass)
        assertEquals(2, (viewModel.state as HomeState.DomainReceivedState).domains.size)

        coVerify(exactly = 1) { getAllDomains.execute(any()) }
        coVerify(exactly = 2) { getAverageLatency.execute(any()) }

    }

    @Test
    fun testFetchDomainsFailure() = runTest {
        coEvery { getAllDomains.execute(any()) }.throws(Exception())

        coEvery { getAverageLatency.execute(any()) }.returns(
            AverageLatencyModel(domain = "google.com", latency = 100, isAvailable = false),
        )

        val viewModel = HomeViewModel(context, getAllDomains, getAverageLatency)
        assertEquals(HomeState.Loading.javaClass, viewModel.state.javaClass)
        viewModel.handleIntent(HomeIntent.GetDomains)
        assertEquals(HomeState.Error(Exception()).javaClass, viewModel.state.javaClass)

        coVerify(exactly = 1) { getAllDomains.execute(any()) }
        coVerify(inverse = true) { getAverageLatency.execute(any()) }

    }

}