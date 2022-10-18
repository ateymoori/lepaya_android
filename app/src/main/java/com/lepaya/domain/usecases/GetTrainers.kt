package com.lepaya.domain.usecases

import com.lepaya.data.utils.Resource
import com.lepaya.domain.entities.TrainerEntity
import com.lepaya.domain.repositories.TrainersRepository
import javax.inject.Inject

class GetTrainers @Inject constructor(
    private val trainersRepository: TrainersRepository
) : UseCase<Nothing?, Resource<List<TrainerEntity>>>() {

    override suspend fun invoke(data: Nothing?): Resource<List<TrainerEntity>> {
        return trainersRepository.getTrainers()
    }

}