package com.lepaya.domain.usecases

import com.lepaya.data.utils.Resource
import com.lepaya.domain.entities.TrainerEntity
import com.lepaya.domain.repositories.TrainersRepository
import javax.inject.Inject

class GetTrainerDetails @Inject constructor(
    private val trainersRepository: TrainersRepository
) : UseCase<Int?, Resource<TrainerEntity>>() {

    override suspend fun invoke(data: Int?): Resource<TrainerEntity> {
        return trainersRepository.getTrainerDetails(data)
    }

}