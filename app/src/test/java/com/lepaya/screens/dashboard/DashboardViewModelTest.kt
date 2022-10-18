package com.lepaya.screens.dashboard

import com.lepaya.data.models.SampleTrainer
import com.lepaya.data.models.mapToTrainerEntity
import com.lepaya.data.utils.Resource
import com.lepaya.domain.repositories.TrainersRepository
import com.lepaya.domain.usecases.FindTrainers
import com.lepaya.domain.usecases.GetTrainers
import com.lepaya.presentation.ui.screens.dashboard.DashboardViewModel
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

class DashboardViewModelTest {
    private lateinit var getTrainers: GetTrainers
    private lateinit var findTrainers: FindTrainers

    @Mock
    private lateinit var trainersRepository: TrainersRepository
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        getTrainers = GetTrainers(trainersRepository)
        findTrainers = FindTrainers(trainersRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `TEST GetTrainers, State and effect of Dashboard ViewModel`() {
        runBlockingTest {
            launch(dispatcher) {
                val results = Resource.Success(
                    listOf(SampleTrainer.mapToTrainerEntity())
                )

                Mockito.`when`(trainersRepository.getTrainers()).thenReturn(results)

                val viewModel = DashboardViewModel(getTrainers, findTrainers)

                Assert.assertEquals(
                    viewModel.viewState.value.trainers,
                    listOf(SampleTrainer.mapToTrainerEntity())
                )
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}