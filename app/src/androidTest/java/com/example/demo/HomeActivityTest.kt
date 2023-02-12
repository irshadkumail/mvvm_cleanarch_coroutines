package com.example.demo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.demo.domain.models.DomainModel
import com.example.demo.presentation.HomeActivity
import com.example.demo.presentation.HomeScreen
import com.example.demo.presentation.HomeState
import com.example.demo.presentation.HomeViewModel
import com.google.accompanist.appcompattheme.AppCompatTheme
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class HomeActivityTest {

    @MockK
    lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)


    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun myTest_Loading() {

        composeTestRule.setContent {
            AppCompatTheme {
                HomeScreen(state = HomeState.Loading, viewModel, onDismiss = {
                    //Do Nothing
                })
            }
        }

        composeTestRule.onNodeWithTag(HomeActivity.LOADING_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(HomeActivity.LIST_TEST_TAG).assertIsNotDisplayed()

    }

    @Test
    fun myTest_Success() {

        val state = HomeState.DomainReceivedState(
            domains = listOf(
                DomainModel(name = "Google", "Google.com", domainIcon = ""),
                DomainModel(name = "Yahoo", "yahoo.co.in", domainIcon = "")
            )
        )

        composeTestRule.setContent {
            AppCompatTheme {
                HomeScreen(state = state, viewModel, onDismiss = {

                })
            }
        }

        composeTestRule.onNodeWithTag(HomeActivity.LOADING_TEST_TAG).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(HomeActivity.LIST_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(HomeActivity.LIST_TEST_TAG)
            .onChildren()
            .assertCountEquals(2)
    }

    @Test
    fun myTest_Failure() {

        val state = HomeState.Error(
            Exception()
        )

        composeTestRule.setContent {
            AppCompatTheme {
                HomeScreen(state = state, viewModel, onDismiss = {
                    //Do Nothing
                })
            }
        }

        composeTestRule.onNodeWithTag(HomeActivity.LOADING_TEST_TAG).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(HomeActivity.LIST_TEST_TAG).assertIsNotDisplayed()

    }
}