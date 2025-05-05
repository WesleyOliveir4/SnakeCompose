package com.example.snakecompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

import com.example.snakecompose.ui.feature.gameover.GameOverScreen
import com.example.snakecompose.ui.feature.home.HomeSnakeGameScreen
import com.example.snakecompose.ui.feature.snakegame.Game
import com.example.snakecompose.ui.feature.snakegame.SnakeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable

@Serializable
object GameRoute

@Serializable
data class GameOverRoute(val points: Int? = null)

@Serializable
object HomeRoute

@Composable
fun SnakeNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeRoute){
        composable<GameRoute> {
            SnakeScreen(
               Game(CoroutineScope(Dispatchers.Default)),
               navigateToGameOverScreen = { points ->
                   navController.navigate(GameOverRoute(points = points ))
               }
           )
        }

        composable<GameOverRoute> {
            val gameOverRoute = it.toRoute<GameOverRoute>()
            GameOverScreen(
                score = gameOverRoute.points ?: 0,
                navigateToGameScreen = {
                    navController.navigate(GameRoute)
                }
            )
        }

        composable<HomeRoute> {
            HomeSnakeGameScreen(
                navigateToGameScreen = {
                    navController.navigate(GameRoute)
                }
            )
        }

    }
}