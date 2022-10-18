package com.lepaya.data.api

import com.lepaya.data.models.TrainerResponse
import retrofit2.Response
import retrofit2.http.GET

interface RestAPI {

    companion object {
        const val BASE_API_URL = "https://5fb52c64e473ab0016a179a0.mockapi.io/api/"
    }

    @GET("v1/employee/employee")
    suspend fun getTrainers(): Response<List<TrainerResponse>>


    ///Simulate the API
    @GET("v1/employee/employee")
    suspend fun findTrainer(): Response<List<TrainerResponse>>


}


