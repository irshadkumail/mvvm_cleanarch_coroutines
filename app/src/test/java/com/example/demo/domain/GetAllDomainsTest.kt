package com.example.demo.domain

import android.content.Context
import com.example.demo.CoroutineTestRule
import com.example.demo.data.model.DomainResponseModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Assert.*
import org.junit.Test

class GetAllDomainsTest {

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
    fun testServerSuccess() = runTest {
        coEvery { repository.getDomains() }.returns(
            listOf(
                DomainResponseModel(
                    name = "",
                    domain = "",
                    image = ""
                )
            )
        )
        coEvery { repository.saveDomainsToCache(any()) }.returns(Unit)

        val getAllDomainsResponse =
            GetAllDomains(context, repository, coroutineTestRule.testDispatcherProvider).execute(
                false
            )

        assertEquals(1, getAllDomainsResponse.size)
        coVerify(exactly = 1) { repository.getDomains() }
        coVerify(exactly = 1) { repository.saveDomainsToCache(any()) }
        coVerify(inverse = true) { repository.getLastSavedDomains() }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testServerFail() = runTest {
        coEvery { repository.getDomains() }.throws(Exception())
        coEvery { repository.saveDomainsToCache(any()) }.returns(Unit)
        coEvery { repository.getLastSavedDomains() }.returns(
            listOf(
                DomainResponseModel(
                    name = "",
                    domain = "",
                    image = ""
                ),
                DomainResponseModel(
                    name = "",
                    domain = "",
                    image = ""
                )
            )
        )


        val getAllDomainsResponse =
            GetAllDomains(context, repository, coroutineTestRule.testDispatcherProvider).execute(
                false
            )

        assertEquals(2, getAllDomainsResponse.size)
        coVerify(exactly = 1) { repository.getDomains() }
        coVerify(inverse = true) { repository.saveDomainsToCache(any()) }
        coVerify(exactly = 1) { repository.getLastSavedDomains() }
    }

    @Test
    fun testException() {
        coEvery { repository.getDomains() }.throws(Exception())
        coEvery { repository.saveDomainsToCache(any()) }.returns(Unit)
        coEvery { repository.getLastSavedDomains() }.throws(Exception())

        assertThrows(Exception::class.java) {
            runTest {
                GetAllDomains(
                    context,
                    repository,
                    coroutineTestRule.testDispatcherProvider
                ).execute(
                    false
                )
            }
        }
    }
}