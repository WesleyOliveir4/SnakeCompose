package com.example.snakecompose.ui.feature.gameover.state

sealed class ScoreState {
    data class ReturnHighScore(val score: Int) : ScoreState()
}