package com.lepaya.presentation.ui.screens.dashboard

import com.lepaya.domain.entities.TrainerEntity
import com.lepaya.presentation.utils.ViewEvent
import com.lepaya.presentation.utils.ViewSideEffect
import com.lepaya.presentation.utils.ViewState

class DashboardContract {
    sealed class Event : ViewEvent {
        data class TrainerSelected(val trainerID: Int?) : Event()
        data class SearchValueChanged(val searchValue: String?) : Event()
    }

    data class State(
        val trainers: List<TrainerEntity> = listOf(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val searchValue: String? = null
    ) :
        ViewState

    sealed class Effect : ViewSideEffect {
        object DataWasLoaded : Effect()
        object GotError : Effect()

        sealed class Navigation : Effect() {
            data class ToTrainerDetails(val trainerID: Int) : Navigation()
        }
    }

}