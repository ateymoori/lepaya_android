package com.lepaya.usecases

import com.lepaya.data.models.SampleTrainer
import com.lepaya.data.models.mapToTrainerEntity
import com.lepaya.data.utils.Resource
import com.lepaya.data.utils.onSuccess
import com.lepaya.domain.repositories.TrainersRepository
import com.lepaya.domain.usecases.GetTrainerDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetTrainerDetailsTest {
    @Mock
    private lateinit var trainersRepository: TrainersRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Test GetTrainerDetails use-case, DI and Repository`() {
        runBlockingTest {
            GetTrainerDetails(trainersRepository).invoke(1)
            Mockito.verify(trainersRepository, Mockito.times(1)).getTrainerDetails(1)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `Test Get Trainer Details if returns correct answer`() {
        runBlockingTest {

            val result = Resource.Success(SampleTrainer.mapToTrainerEntity())

            Mockito.`when`(trainersRepository.getTrainerDetails(1)).thenReturn(result)

            val useCase = GetTrainerDetails(trainersRepository)

            useCase.invoke()
                .onSuccess {
                    Assert.assertEquals(it?.first_name, SampleTrainer.name.first)
                }
        }
    }


}