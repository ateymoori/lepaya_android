package com.lepaya.domain.usecases

import com.lepaya.data.utils.Resource
import com.lepaya.domain.entities.TrainerEntity
import com.lepaya.domain.repositories.TrainersRepository
import javax.inject.Inject

class FindTrainers @Inject constructor(
    private val trainersRepository: TrainersRepository
) : UseCase<String?, Resource<List<TrainerEntity>>>() {

    //to let test classes have access to these values
    companion object {
        private val MINIMUM_SEARCH_WORDS_LENGTH = 3
        val EMPTY_WORDS_ERROR = "Search string is mandatory"
        val LENGTH_WORDS_ERROR = "need $MINIMUM_SEARCH_WORDS_LENGTH char to search at least"
    }

    override suspend fun invoke(data: String?): Resource<List<TrainerEntity>> {
        return when {
            data.isNullOrEmpty() -> {
                Resource.Failure.Generic(EMPTY_WORDS_ERROR)
            }
            data.length < MINIMUM_SEARCH_WORDS_LENGTH -> {
                Resource.Failure.Generic(LENGTH_WORDS_ERROR)
            }
            else -> {
                trainersRepository.findTrainers(data)
            }
        }

    }

}