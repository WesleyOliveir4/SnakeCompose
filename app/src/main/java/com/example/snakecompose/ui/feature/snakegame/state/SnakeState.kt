package com.example.snakecompose.ui.feature.snakegame.state

data class SnakeGameState (
    val food: Pair<Int, Int>,
    val snake: List<Pair<Int, Int>>
)