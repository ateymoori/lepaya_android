package com.lepaya.screens.details

import androidx.lifecycle.SavedStateHandle
import com.lepaya.data.models.SampleTrainer
import com.lepaya.data.models.mapToTrainerEntity
import com.lepaya.data.utils.Resource
import com.lepaya.domain.repositories.TrainersRepository
import com.lepaya.domain.usecases.GetTrainerDetails
import com.lepaya.presentation.ui.screens.details.DetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailsViewModelTest {
    private lateinit var getTrainerDetails: GetTrainerDetails
    private lateinit var stateHandle: SavedStateHandle

    @Mock
    private lateinit var trainersRepository: TrainersRepository
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        getTrainerDetails = GetTrainerDetails(trainersRepository)
        stateHandle = SavedStateHandle()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `TEST GetTrainerDetails, State and effect of Details ViewModel`() {
        runBlockingTest {
            launch(dispatcher) {
                val results = Resource.Success(SampleTrainer.mapToTrainerEntity())

                Mockito.`when`(trainersRepository.getTrainerDetails(1)).thenReturn(results)
                val viewModel = DetailsViewModel(
                    stateHandle = stateHandle,
                    getTrainerDetails = getTrainerDetails
                )
                viewModel.getDetails(1)
                Assert.assertEquals(viewModel.viewState.value.trainer, SampleTrainer.mapToTrainerEntity())
            }
        }
    }



    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}