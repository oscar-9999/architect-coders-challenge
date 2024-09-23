package com.acchallenge.rickandmorty.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.acchallenge.rickandmorty.ui.screens.detail.CharacterDetailsScreen
import com.acchallenge.rickandmorty.ui.screens.listcharacters.CharacterList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Characters.CHARACTERlIST
    ) {
        composable(
            route = Characters.CHARACTERlIST
        ) {
            CharacterList(
                navigate = { characterId ->
                    navController.navigate(Characters.CHARACTERDETAILS + "/$characterId") {
                        launchSingleTop
                    }
                }
            )
        }
        composable(
            route = Characters.CHARACTERDETAILS + "/{characterId}",
            arguments = listOf(
                navArgument(
                    name = "characterId",
                ) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId")
            characterId?.let {
                CharacterDetailsScreen(
                    { navController.popBackStack() }
                )
            }
        }
    }
}
