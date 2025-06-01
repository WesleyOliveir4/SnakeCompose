package com.example.snakecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.snakecompose.navigation.SnakeNavHost
import com.example.snakecompose.ui.theme.SnakeComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SnakeComposeTheme {
                SnakeNavHost()
            }
        }
    }

}