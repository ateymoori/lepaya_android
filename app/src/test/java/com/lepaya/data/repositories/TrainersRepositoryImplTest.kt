package com.lepaya.data.repositories

import com.lepaya.data.api.RestAPI
import com.lepaya.data.models.SampleTrainer
import com.lepaya.data.models.TrainerResponse
import com.lepaya.data.utils.onError
import com.lepaya.data.utils.onSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class TrainersRepositoryImplTest {
    @Mock
    private lateinit var restApi: RestAPI

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Test Get Trainers List and TrainersEntityList mapper`() {
        runBlockingTest {
                val results = listOf(SampleTrainer,SampleTrainer,SampleTrainer)
            val mockResponse =
                Response.success(results)

            Mockito.`when`(restApi.getTrainers()).thenReturn(mockResponse)

            val trainersRepositoryImpl = TrainersRepositoryImpl(restApi)

            trainersRepositoryImpl.getTrainers().onSuccess {
                Assert.assertEquals(it?.size , results.size)
            }.onError {
                assert(false)
            }

        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Test Get Trainer Details `() {
        runBlockingTest {

            val mockResponse =
                Response.success(listOf(SampleTrainer))

            Mockito.`when`(restApi.getTrainers()).thenReturn(mockResponse)

            val trainersRepositoryImpl = TrainersRepositoryImpl(restApi)

            trainersRepositoryImpl.getTrainerDetails(1).onSuccess {
                Assert.assertEquals(it?.first_name, SampleTrainer.name.first)
                Assert.assertEquals(it?.last_name, SampleTrainer.name.last)
                Assert.assertEquals(it?.picture, SampleTrainer.picture)
            }.onError {
                assert(false)
            }

        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `Test Find Trainer `() {
        runBlockingTest {

            val mockResponse =
                Response.success(listOf(SampleTrainer))

            Mockito.`when`(restApi.findTrainer()).thenReturn(mockResponse)

            val trainersRepositoryImpl = TrainersRepositoryImpl(restApi)

            trainersRepositoryImpl.findTrainers("Williamson").onSuccess {
                Assert.assertNotNull(it)
            }.onError {
                assert(false)
            }

        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Test REST API Error Handling`() {
        val errorMsg = "You don't have access to this API."
        runBlockingTest {
            val errorResponse = """
                {
                       "type" : "error",
                       "message" : "$errorMsg"
                }
            """.trimIndent()

            val errorResponseBody =
                errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
            val mockResponse = Response.error<List<TrainerResponse>>(403, errorResponseBody)

            Mockito.`when`(restApi.getTrainers()).thenReturn(mockResponse)

            val trainersRepositoryImpl = TrainersRepositoryImpl(restApi)

            trainersRepositoryImpl.getTrainers().onSuccess {
                assert(false)
            }.onError {
                Assert.assertEquals(it, errorMsg)
            }
        }
    }

}