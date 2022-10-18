package com.lepaya.usecases

import com.lepaya.data.models.SampleTrainer
import com.lepaya.data.models.mapToTrainerEntity
import com.lepaya.domain.repositories.TrainersRepository
import com.lepaya.domain.usecases.GetTrainers
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import com.lepaya.data.utils.Resource
import com.lepaya.data.utils.onNetworkError
import com.lepaya.data.utils.onSuccess

class GetTrainersTest {
    @Mock
    private lateinit var trainersRepository: TrainersRepository


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    /*
        TrainersRepository injected into the GetTrainers UseCase class.
        By calling the GetTrainers`s invoke method, trainersRepository.getTrainers method should be called.
     */
    @ExperimentalCoroutinesApi
    @Test
    fun `Test if GetTrainers UseCase can call trainersRepository getters correct(DI and Interface works fine)`() {
        runBlockingTest {
            GetTrainers(trainersRepository).invoke()
            verify(trainersRepository, times(1)).getTrainers()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Test if the mocked data is equaled to the result of repository and use-case in successful scenario`() {
        runBlockingTest {
            val results = Resource.Success(
                listOf(SampleTrainer.mapToTrainerEntity())
            )

            Mockito.`when`(trainersRepository.getTrainers()).thenReturn(results)

            val useCase = GetTrainers(trainersRepository)

            useCase.invoke()
                .onSuccess {
                    assertEquals(it?.get(0)?.first_name, SampleTrainer.name.first)
                }
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `Test if the mocked data is equaled to the result of repository and use-case in failed scenario`() {
        runBlockingTest {
            val errorMsg = "No Internet connection"

            val results = Resource.Failure.NetworkException(errorMsg)

            Mockito.`when`(trainersRepository.getTrainers()).thenReturn(results)

            val useCase = GetTrainers(trainersRepository)

            useCase.invoke()
                .onNetworkError {
                    assertEquals(it, errorMsg)
                }
        }
    }
}