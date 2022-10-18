package com.lepaya.presentation.ui.screens.dashboard

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.lepaya.R
import com.lepaya.domain.entities.TrainerEntity
import com.lepaya.presentation.ui.components.LoadingBarView
import com.lepaya.presentation.ui.components.TrainersListItemView
import com.lepaya.presentation.ui.components.SearchBarView
import com.lepaya.presentation.ui.screens.entry.EntryPointActivity
import com.lepaya.presentation.ui.theme.TrainersAppTheme
import com.lepaya.presentation.utils.EspressoIdlingResource
import com.lepaya.presentation.utils.LAUNCH_LISTEN_FOR_EFFECTS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(navController: NavHostController) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val state = viewModel.viewState.value

    DashboardView(
        state = state,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        navController = navController,
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is DashboardContract.Effect.Navigation.ToTrainerDetails) {
                navController.navigate("${EntryPointActivity.NavigationKeys.Route.TRAINER_DETAILS}/${navigationEffect.trainerID}")
            }
        })
}

@Composable
fun DashboardView(
    state: DashboardContract.State,
    effectFlow: Flow<DashboardContract.Effect>?,
    onEventSent: (event: DashboardContract.Event) -> Unit,
    navController: NavHostController?,
    onNavigationRequested: (navigationEffect: DashboardContract.Effect.Navigation) -> Unit
) {
    val activity = (LocalContext.current as? Activity)
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var inSearchMode by remember { mutableStateOf(false) }
    // Listen for side effects from the VM
    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is DashboardContract.Effect.DataWasLoaded -> {}

                is DashboardContract.Effect.Navigation.ToTrainerDetails -> onNavigationRequested(
                    effect
                )
                DashboardContract.Effect.GotError -> {

                }
            }
        }?.collect()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                actions = {
                    IconButton(onClick = {
                        inSearchMode = !inSearchMode
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = null, modifier = Modifier.testTag("searchButton"))
                    }
                },
                navigationIcon = {
                    if (inSearchMode) {
                        IconButton(
                            onClick = {
                                inSearchMode = false
                            }
                        ) {
                            Icon(Icons.Filled.ArrowBack, "")
                        }
                    }
                },

                title = {
                    if (!inSearchMode) {
                        Text(modifier = Modifier.testTag("pageTitle") , text = stringResource(R.string.app_name))
                    } else {
                        SearchBarView(placeholderText = "Search a Trainer", searchText =
                        state.searchValue ?: "",
                            onSearchTextChanged = {
                                onEventSent(DashboardContract.Event.SearchValueChanged(it))
                            })
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
            )

        },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TrainersList(trainers = state.trainers) { itemId ->
                onEventSent(DashboardContract.Event.TrainerSelected(itemId))
            }
            if (state.isLoading)
                LoadingBarView()
        }
    }

    //handle onBackPressed
    BackHandler {
        if (inSearchMode) {
            inSearchMode = false
            onEventSent(DashboardContract.Event.SearchValueChanged(""))
        } else {
            activity?.finish()
        }
    }
}

@Composable
fun TrainersList(
    trainers: List<TrainerEntity>?,
    onItemClicked: (id: Int?) -> Unit = { }
) {

    LazyColumn(
        modifier = Modifier.testTag("trainersList"),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        EspressoIdlingResource.increment()
        items(trainers ?: listOf()) { item ->
            TrainerItemRow(item = item, onItemClicked = onItemClicked)
        }
        EspressoIdlingResource.decrement()
    }
}

@ExperimentalCoilApi
@Composable
fun TrainerItemRow(
    item: TrainerEntity,
    onItemClicked: (id: Int?) -> Unit = { }
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(start = 12.dp, end = 12.dp, top = 12.dp)
            .clickable { onItemClicked(1) }
    ) {
        TrainersListItemView(item)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TrainersAppTheme {
        DashboardView(DashboardContract.State(), null, { }, null, {})
    }
}
