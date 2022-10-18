package com.lepaya.usecases

import com.lepaya.data.utils.onError
import com.lepaya.data.utils.onSuccess
import com.lepaya.domain.repositories.TrainersRepository
import com.lepaya.domain.usecases.FindTrainers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FindTrainersTest {
    @Mock
    private lateinit var trainersRepository: TrainersRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Test if DI, FindTrainersUseCase and repository working fine`() {
        runBlockingTest {
            FindTrainers(trainersRepository).invoke("Bertie")
            Mockito.verify(trainersRepository, Mockito.times(1)).findTrainers("Bertie")
        }
    }

    /*
        invoke arg should be filled with search word
        in this case, I've passed null value as search word
        should return error (onError)
     */
    @ExperimentalCoroutinesApi
    @Test
    fun `Test if search word is empty, UseCase should return proper error`() {
        runBlockingTest {
            FindTrainers(trainersRepository).invoke(null)
                .onSuccess {
                    assert(false)
                }.onError {
                    Assert.assertEquals(it, FindTrainers.EMPTY_WORDS_ERROR)
                }
        }
    }

    /*
    invoke arg should be filled with search word
    in this case, I've passed null value as search word
    should return error (onError)
 */
    @ExperimentalCoroutinesApi
    @Test
    fun `Test if search word's length is less than enough, UseCase should return proper error`() {
        runBlockingTest {
            FindTrainers(trainersRepository).invoke("a")
                .onSuccess {
                    assert(false)
                }.onError {
                    Assert.assertEquals(it, FindTrainers.LENGTH_WORDS_ERROR)
                }
        }
    }

}