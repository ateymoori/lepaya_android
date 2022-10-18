package com.lepaya.data.repositories

import com.lepaya.data.api.RestAPI
import com.lepaya.data.models.mapToTrainersEntity
import com.lepaya.data.utils.BaseDataSource
import com.lepaya.data.utils.Resource
import com.lepaya.data.utils.onError
import com.lepaya.data.utils.onSuccess
import com.lepaya.domain.entities.TrainerEntity
import com.lepaya.domain.repositories.TrainersRepository
import javax.inject.Inject
import kotlin.random.Random

class TrainersRepositoryImpl  @Inject constructor(
    private val restApi: RestAPI,
) : TrainersRepository, BaseDataSource()  {

    override suspend fun getTrainers(): Resource<List<TrainerEntity>> {
        getResult { restApi.getTrainers() }.onSuccess {
            return Resource.Success(it?.mapToTrainersEntity())
        }.onError {
            return Resource.Failure.Generic(it)
        }
        return Resource.Failure.NetworkException(null)
    }

    //All details are same
    //I am returning a random item as the trainer
    override suspend fun getTrainerDetails(id: Int?): Resource<TrainerEntity> {
        getResult { restApi.getTrainers() }.onSuccess {
            return Resource.Success(it?.mapToTrainersEntity()?.random())
        }.onError {
            return Resource.Failure.Generic(it)
        }
        return Resource.Failure.NetworkException(null)
    }

    //All details are same
    //I am returning a random elements for find function
    override suspend fun findTrainers(word: String?): Resource<List<TrainerEntity>> {
        getResult { restApi.findTrainer() }.onSuccess {
            return Resource.Success(it?.mapToTrainersEntity()?.take(Random.nextInt(1,4)) )
        }.onError {
            return Resource.Failure.Generic(it)
        }
        return Resource.Failure.NetworkException(null)
    }

}