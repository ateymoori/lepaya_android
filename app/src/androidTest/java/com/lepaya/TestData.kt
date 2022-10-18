package com.lepaya

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lepaya.presentation.ui.screens.entry.EntryPointActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestData {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<EntryPointActivity>()

    @Test
    fun testTrainersListHasRecord() {
        val trainersListItem =
            composeTestRule.onNode(hasTestTag("trainersList"), useUnmergedTree = true)
        trainersListItem.onChildren().get(0).assertIsDisplayed()
    }

}