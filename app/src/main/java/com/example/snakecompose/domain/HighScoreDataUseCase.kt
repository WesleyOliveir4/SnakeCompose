package com.example.snakecompose.domain

interface HighScoreDataUseCase {

    suspend fun getHighScore(): Int
    suspend fun saveHighScore(points: Int)

}