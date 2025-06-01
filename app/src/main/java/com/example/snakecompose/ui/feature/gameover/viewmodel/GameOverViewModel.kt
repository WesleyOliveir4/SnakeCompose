package com.example.snakecompose.ui.feature.gameover.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snakecompose.domain.HighScoreDataUseCase
import com.example.snakecompose.ui.feature.gameover.state.ScoreState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GameOverViewModel(
    private val highScoreDataUseCase: HighScoreDataUseCase
): ViewModel() {

    private val mutableStateHighScore =
        MutableStateFlow(ScoreState.ReturnHighScore(0))
    val stateFieldGame: Flow<ScoreState.ReturnHighScore> = mutableStateHighScore

     fun getHighScore() {
         viewModelScope.launch{
             mutableStateHighScore.value = ScoreState.ReturnHighScore(
                 highScoreDataUseCase.getHighScore()
             )
         }
     }

     fun saveHighScore(points: Int) {
         viewModelScope.launch {
             if( points > highScoreDataUseCase.getHighScore()){
             highScoreDataUseCase.saveHighScore(points)
            }
         }
    }

}