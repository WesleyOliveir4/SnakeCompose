package com.example.snakecompose.ui.feature.snakegame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snakecompose.ui.feature.snakegame.state.SnakeGameState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class SnakeViewModel(): ViewModel() {

    private val mutex = Mutex()
    private val mutableStateFieldGame =
        MutableStateFlow(SnakeGameState(food = Pair(5, 5), snake = listOf(Pair(7, 7))))
    val stateFieldGame: Flow<SnakeGameState> = mutableStateFieldGame

    private val mutablePoints = MutableStateFlow(0)
    val points: Flow<Int> = mutablePoints

    private val mutableGameOverPoints = MutableStateFlow(0)
    val gameOverPoints: Flow<Int> = mutableGameOverPoints

    var move = MutableStateFlow(Pair(1, 0))
        set(value) {
            viewModelScope.launch {
                mutex.withLock {
                    value
                }
            }
        }


    companion object {
        const val BOARD_SIZE = 16
    }


    init {
        viewModelScope.launch {
            var snakeLength = 4

            while (true) {
                delay(150)
                mutableStateFieldGame.update {
                    var newPosition = it.snake.first().let { position ->

                        mutex.withLock {
                            Pair(
                                (position.first + move.value.first + BOARD_SIZE) % BOARD_SIZE,
                                (position.second + move.value.second + BOARD_SIZE) % BOARD_SIZE
                            )

                        }
                    }

                    if (newPosition == it.food) {
                        increasePoints()
                        snakeLength++
                    }

                    if (it.snake.contains(newPosition)) {
                        gameOver(snakeLength)
                        snakeLength = 4
                    }

                    it.copy(
                        food = if (newPosition == it.food) {
                            Pair(
                                Random.nextInt(BOARD_SIZE),
                                Random.nextInt(BOARD_SIZE)
                            )
                        } else {
                            it.food
                        },
                        snake = listOf(newPosition) + it.snake.take(snakeLength - 1)
                    )
                }
            }
        }
    }

    private fun increasePoints() {
        viewModelScope.launch {
            mutex.withLock {
                mutablePoints.update {
                    it + 1
                }
            }
        }
    }

    private fun gameOver(points: Int) {
        viewModelScope.launch {
            mutex.withLock {
                mutableGameOverPoints.update {
                    points - 4
                }
            }
        }
    }

}