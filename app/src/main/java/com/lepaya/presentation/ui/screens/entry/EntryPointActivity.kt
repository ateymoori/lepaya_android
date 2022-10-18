package com.lepaya.presentation.ui.screens.entry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lepaya.presentation.ui.screens.dashboard.DashboardScreen
import com.lepaya.presentation.ui.screens.details.DetailsScreen
import com.lepaya.presentation.ui.screens.entry.EntryPointActivity.NavigationKeys.Arg.TRAINER
import com.lepaya.presentation.ui.theme.TrainersAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryPointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrainersAppTheme {
                TrainerApp()
            }
        }
    }

    @Composable
    fun TrainerApp() {
        val navController = rememberNavController()
        NavHost(navController, startDestination = NavigationKeys.Route.TRAINERS_LIST) {
            composable(route = NavigationKeys.Route.TRAINERS_LIST) {
                DashboardScreen(navController)
            }
            composable(
                route = "${NavigationKeys.Route.TRAINER_DETAILS}/{${TRAINER}}",
                arguments = listOf(navArgument(TRAINER) {
                    type = NavType.IntType
                })
            ) {
                DetailsScreen(navController)
            }
        }
    }


    object NavigationKeys {
        object Arg {
            const val TRAINER = "trainer"
        }

        object Route {
            const val TRAINERS_LIST = "trainers_list"
            const val TRAINER_DETAILS = "trainer_details"
        }
    }
}