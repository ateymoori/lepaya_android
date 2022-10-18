package com.lepaya

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import com.lepaya.data.models.SampleTrainer
import com.lepaya.data.models.mapToTrainerEntity
import com.lepaya.presentation.ui.screens.dashboard.TrainersList
import com.lepaya.presentation.ui.screens.entry.EntryPointActivity
import com.lepaya.presentation.ui.theme.TrainersAppTheme
import com.lepaya.presentation.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TrainersListTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<EntryPointActivity>()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unRegisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }


    @Before
    fun initList() {
        composeTestRule.setContent {
            TrainersAppTheme {
                TrainersList(listOf(SampleTrainer.mapToTrainerEntity()))
            }
        }
    }

    @Test
    fun testTrainersListHasRecord() {
        val trainersListItem =
            composeTestRule.onNode(hasTestTag("trainersList"), useUnmergedTree = true)
        trainersListItem.onChildren().assertCountEquals(1)
    }

}