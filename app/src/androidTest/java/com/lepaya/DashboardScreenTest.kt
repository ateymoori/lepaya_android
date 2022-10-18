package com.lepaya


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.lepaya.presentation.ui.screens.dashboard.DashboardContract
import com.lepaya.presentation.ui.screens.dashboard.DashboardView
import com.lepaya.presentation.ui.screens.entry.EntryPointActivity
import com.lepaya.presentation.ui.theme.TrainersAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<EntryPointActivity>()

    @Before
    fun initList() {
        composeTestRule.setContent {
            TrainersAppTheme {
                DashboardView(state = DashboardContract.State(
                    trainers = listOf(),
                    isLoading = false,
                    error = null,
                    searchValue = null
                ),
                    effectFlow = null,
                    onEventSent = {},
                    navController = null,
                    onNavigationRequested = {})
            }
        }
    }

    /**
     * after click on search button in the dashboard,
     * page title should be hide and search field should be appeared.
     */
    @Test
    fun testSearchButton() {

        val searchButton =
            composeTestRule.onNode(hasTestTag("searchButton"), useUnmergedTree = true)

        val pageTitle =
            composeTestRule.onNode(hasTestTag("pageTitle"), useUnmergedTree = true)

        val searchField =
            composeTestRule.onNode(hasTestTag("searchField"), useUnmergedTree = true)

        pageTitle.assertIsDisplayed()

        searchButton.performClick()

        pageTitle.assertDoesNotExist()
        searchField.assertIsDisplayed()
    }


}