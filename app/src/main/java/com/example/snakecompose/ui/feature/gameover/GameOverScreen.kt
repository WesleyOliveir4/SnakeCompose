package com.example.snakecompose.ui.feature.gameover

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snakecompose.ui.theme.SnakeComposeTheme

@Composable
fun GameOverScreen(
     score: Int,
     navigateToGameScreen: () -> Unit = {}
){
        SnakeComposeTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                GameOverContent(score, navigateToGameScreen)
            }
        }
}

@Composable
fun GameOverContent(score: Int,navigateToGameScreen: () -> Unit = {}) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToGameScreen()
                }
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Restart game")
            }
        }
    ){ paddinValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddinValues)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 85.dp),
                text = "Game Over",
                textAlign = TextAlign.Center,
                fontSize = 35.sp,
            )

            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 15.dp),
                text = "Points: $score",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )
        }

    }

}



@Preview(showBackground = true)
@Composable
fun GameOverPreview(){
    val score = 10
    SnakeComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ){
            GameOverContent(score)
        }
    }
}