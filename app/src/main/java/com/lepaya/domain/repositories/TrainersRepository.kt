package com.lepaya.domain.repositories

import com.lepaya.data.utils.Resource
import com.lepaya.domain.entities.TrainerEntity

interface TrainersRepository {
    suspend fun getTrainers(): Resource<List<TrainerEntity>>
    suspend fun getTrainerDetails(id: Int?): Resource<TrainerEntity>
    suspend fun findTrainers(word: String?): Resource<List<TrainerEntity>>
}