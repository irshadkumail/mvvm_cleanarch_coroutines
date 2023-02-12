package com.example.demo.domain

import android.content.Context
import com.example.demo.CoroutineTestRule
import com.example.demo.data.model.DomainResponseModel
import com.example.demo.domain.models.DomainModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import java.lang.Exception

class GetAverageLatencyTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var repository: DomainRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSuccess() = runTest {
        val requestModel =
            DomainModel(
                name = "https://google.com",
                domainUrl = "https://google.com",
                domainIcon = ""
            )

        coEvery { repository.pingDomain(requestModel.domainUrl) }.returns(
            Response.Builder()
                .code(200)
                .protocol(Protocol.HTTP_1_0)
                .request(
                    Request.Builder()
                        .url(requestModel.domainUrl)
                        .build()
                )
                .message("")
                .receivedResponseAtMillis(800)
                .sentRequestAtMillis(200)
                .build()
        )

        val getAverageLatency =
            GetAverageLatency(
                context,
                repository,
                coroutineTestRule.testDispatcherProvider
            ).execute(requestModel)

        assertEquals(5, GetAverageLatency.MAX_ATTEMPTS)
        assertEquals(true, getAverageLatency.isAvailable)
        assertEquals("https://google.com", getAverageLatency.domain)
        assertEquals(600, getAverageLatency.latency)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testException() = runTest {
        val requestModel =
            DomainModel(
                name = "https://google.com",
                domainUrl = "https://google.com",
                domainIcon = ""
            )

        coEvery { repository.pingDomain(requestModel.domainUrl) }.throws(Exception())

        val getAverageLatency =
            GetAverageLatency(
                context,
                repository,
                coroutineTestRule.testDispatcherProvider
            ).execute(requestModel)

        assertEquals(5, GetAverageLatency.MAX_ATTEMPTS)
        assertEquals(false, getAverageLatency.isAvailable)
        assertEquals("https://google.com", getAverageLatency.domain)
        assertEquals(0, getAverageLatency.latency)
    }


}