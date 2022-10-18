package com.lepaya.presentation.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lepaya.data.utils.onError
import com.lepaya.data.utils.onSuccess
import com.lepaya.domain.usecases.GetTrainerDetails
import com.lepaya.presentation.ui.screens.entry.EntryPointActivity
import com.lepaya.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val getTrainerDetails: GetTrainerDetails
) :
    BaseViewModel<DetailsContract.Event, DetailsContract.State, DetailsContract.Effect>() {

    init {
        viewModelScope.launch {
            getDetails(
                stateHandle.get<Int>(EntryPointActivity.NavigationKeys.Arg.TRAINER)
            )
        }
    }

    override fun setInitialState() =
        DetailsContract.State(trainer = null, isLoading = true)

    override fun handleEvents(event: DetailsContract.Event) {

    }

      suspend fun getDetails(trainerID: Int?) {
        getTrainerDetails.invoke(trainerID).onSuccess {
            setState {
                copy(trainer = it, isLoading = false, error = null)
            }
            setEffect { DetailsContract.Effect.DataWasLoaded }
        }.onError {
            setState {
                copy(trainer = trainer, isLoading = false, error = it)
            }
        }
    }


}
