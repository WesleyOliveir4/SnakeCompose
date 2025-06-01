package com.example.snakecompose.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.snakecompose.domain.HighScoreDataUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HighScoreDataImpl(private val context: Context): HighScoreDataUseCase {

    private object PreferencesKeys {
        val HIGH_SCORE = intPreferencesKey("high_score")
    }

    override suspend fun getHighScore(): Int {
        return context.dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.HIGH_SCORE] ?: 0
            }.first()
    }

    override suspend fun saveHighScore(points: Int) {
        context.dataStore.edit { settings ->
            val currentHighScore = settings[PreferencesKeys.HIGH_SCORE] ?: 0
            if (points > currentHighScore) {
                settings[PreferencesKeys.HIGH_SCORE] = points
            }
        }
    }
}