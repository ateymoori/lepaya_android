package com.lepaya.presentation.ui.screens.dashboard

import androidx.lifecycle.viewModelScope
import com.lepaya.data.utils.onError
import com.lepaya.data.utils.onSuccess
import com.lepaya.domain.usecases.FindTrainers
import com.lepaya.domain.usecases.GetTrainers
import com.lepaya.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getTrainers: GetTrainers,
    private val findTrainers: FindTrainers
) :
    BaseViewModel<DashboardContract.Event, DashboardContract.State, DashboardContract.Effect>() {

    init {
        viewModelScope.launch { getTrainers() }
    }

    override fun setInitialState() =
        DashboardContract.State(trainers = listOf(), isLoading = true)

    override fun handleEvents(event: DashboardContract.Event) {
        when (event) {
            is DashboardContract.Event.TrainerSelected -> {
                setEffect {
                    DashboardContract.Effect.Navigation.ToTrainerDetails(
                        event.trainerID ?: 0
                    )
                }
            }
            is DashboardContract.Event.SearchValueChanged -> {
                setState {
                    viewModelScope.launch {
                        if (event.searchValue.isNullOrEmpty())
                            getTrainers()
                        else
                            findTrainer(event.searchValue)
                    }
                    copy(isLoading = true, searchValue = event.searchValue)
                }
            }
        }
    }

    private suspend fun getTrainers() {
        getTrainers.invoke().onSuccess {
            setState {
                copy(trainers = it ?: listOf(), isLoading = false, error = null)
            }
            setEffect { DashboardContract.Effect.DataWasLoaded }
        }.onError {
            setState {
                copy(trainers = trainers, isLoading = false, error = it)
            }
        }
    }

    suspend fun findTrainer(word: String?) {
        findTrainers.invoke(word).onSuccess {
            setState {
                copy(trainers = it ?: listOf(), isLoading = false, error = null)
            }
            setEffect { DashboardContract.Effect.DataWasLoaded }
        }.onError {
            setState {
                copy(trainers = trainers, isLoading = false, error = it)
            }
            setEffect { DashboardContract.Effect.GotError }
        }
    }


}
