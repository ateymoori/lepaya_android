package com.lepaya.presentation.ui.screens.details

import com.lepaya.domain.entities.TrainerEntity
import com.lepaya.presentation.utils.ViewEvent
import com.lepaya.presentation.utils.ViewSideEffect
import com.lepaya.presentation.utils.ViewState

class DetailsContract {
    sealed class Event : ViewEvent {
    }

    data class State(
        val trainer: TrainerEntity? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    ) :
        ViewState

    sealed class Effect : ViewSideEffect {
        object DataWasLoaded : Effect()
        object GotError : Effect()
    }
}