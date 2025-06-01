package com.example.snakecompose.ui.feature.gameover

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snakecompose.R
import com.example.snakecompose.ui.feature.gameover.state.ScoreState
import com.example.snakecompose.ui.feature.gameover.viewmodel.GameOverViewModel
import com.example.snakecompose.ui.theme.SnakeComposeTheme
import kotlinx.coroutines.coroutineScope
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameOverScreen(
     score: Int,
     navigateToGameScreen: () -> Unit = {}
){
        SnakeComposeTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(4285563448)
            ) {
                val gameOverViewModel: GameOverViewModel = koinViewModel()

                GameOverContent(score, navigateToGameScreen, gameOverViewModel )
            }
        }
}

@Composable
fun GameOverContent(
    score: Int,
    navigateToGameScreen: () -> Unit = {},
    gameOverViewModel: GameOverViewModel
) {


    gameOverViewModel.saveHighScore(score)
    gameOverViewModel.getHighScore()
    val state = gameOverViewModel.stateFieldGame.collectAsState(
        initial = ScoreState.ReturnHighScore(0)
    )


    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                contentColor = White,
                containerColor = Color(4283126560),
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
                text = "Score: $score",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )

            Image(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 15.dp)
                    .size(120.dp,120.dp),
                painter = painterResource(id = R.drawable.snake),
                contentDescription = "Game Over"
            )

            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 15.dp),
                text = "[Record High Score]",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = White
            )

            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 15.dp),
                text = "${state.value.score}",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = White
            )


        }

    }

}



@Preview(showBackground = true)
@Composable
fun GameOverPreview(){
    val score = 10
    val gameOverViewModel: GameOverViewModel = koinViewModel()
    SnakeComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(4285563448)
        ){
            GameOverContent(score, gameOverViewModel = gameOverViewModel)
        }
    }
}