package com.example.snakecompose.di

import com.example.snakecompose.data.HighScoreDataImpl
import com.example.snakecompose.domain.HighScoreDataUseCase
import com.example.snakecompose.ui.feature.gameover.viewmodel.GameOverViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gameOverModule = module {

    factory<HighScoreDataUseCase> {
        HighScoreDataImpl(androidContext())
    }

    viewModel { GameOverViewModel(
        highScoreDataUseCase = get()
    ) }
}