package com.example.snakecompose.ui.feature.snakegame.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults.Small
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snakecompose.R
import com.example.snakecompose.ui.feature.snakegame.state.SnakeGameState
import com.example.snakecompose.ui.feature.snakegame.viewmodel.SnakeViewModel
import com.example.snakecompose.ui.theme.SnakeComposeTheme

class Game() {
    companion object {
        const val BOARD_SIZE = 16
    }
}


@Composable
fun SnakeScreen(
    snakeViewModel: SnakeViewModel,
    navigateToGameOverScreen: (points: Int?) -> Unit = {}
) {
    val stateFieldGame = snakeViewModel.stateFieldGame.collectAsState(initial = null)
    val points = snakeViewModel.points.collectAsState(initial = 0)
    val gameOverPoints = snakeViewModel.gameOverPoints.collectAsState(initial = 0)

    when (gameOverPoints.value) {
        0 -> {}
        else -> {
            navigateToGameOverScreen(gameOverPoints.value)
        }
    }
    SnakeComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(4285563448)
        ) {
            SnakeContent(stateFieldGame.value, points.value, snakeViewModel)
        }
    }

}

@Composable
fun SnakeContent(
    snakeGameState: SnakeGameState?,
    points: Int,
    snakeViewModel: SnakeViewModel
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        snakeGameState?.let {
            Board(it)
        }
        scoreboard(points)
        Buttons {
            snakeViewModel.move.value = it
        }
    }
}

@Composable
fun scoreboard(points: Int = 0) {
    Box(
        Modifier
            .size(width = 100.dp, height = 60.dp)
            .border(2.dp, Color(4283126560)),
    ) {
        Text(
            text = points.toString(),
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp),
            fontWeight = FontWeight.Bold,
            color = Color(4283126560)
        )
    }
}

@Composable
fun Buttons(onDirectionChange: (Pair<Int, Int>) -> Unit) {
    val buttonsSize = Modifier.size(64.dp)
    val buttonsColor = ButtonColors(Color(4283126560), White, White, White)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 100.dp, vertical = 30.dp)
    ) {

        Button(
            onClick = { onDirectionChange(Pair(0, -1)) },
            modifier = buttonsSize,
            colors = buttonsColor
        ) {
            Icon(Icons.Default.KeyboardArrowUp, null)
        }
        Row {
            Button(
                onClick = { onDirectionChange(Pair(-1, 0)) },
                modifier = buttonsSize,
                colors = buttonsColor
            ) {
                Icon(Icons.Default.KeyboardArrowLeft, null)
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onDirectionChange(Pair(1, 0)) },
                modifier = buttonsSize,
                colors = buttonsColor
            ) {
                Icon(Icons.Default.KeyboardArrowRight, null)
            }
        }

        Button(
            onClick = { onDirectionChange(Pair(0, 1)) },
            modifier = buttonsSize,
            colors = buttonsColor
        ) {
            Icon(Icons.Default.KeyboardArrowDown, null)
        }
    }
}


@Composable
fun Board(snakeGameState: SnakeGameState) {
    BoxWithConstraints(Modifier.padding(16.dp)) {
        val tileSize = maxWidth / Game.BOARD_SIZE
        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, Color(4283126560))
        )

        Box(
            Modifier.offset(
                x = tileSize * snakeGameState.food.first,
                y = tileSize * snakeGameState.food.second
            ).size(tileSize)
        ){
            Image(
                painter = painterResource(id = R.drawable.apple),
                contentDescription = "Food Icon",
                modifier = Modifier.fillMaxSize(),
            )
        }

        snakeGameState.snake.forEach {
            Box(
                Modifier.offset(
                    x = tileSize * it.first,
                    y = tileSize * it.second
                ).size(tileSize)
            ){
                if(it == snakeGameState.snake.first()){
                    Image(
                        painter = painterResource(id = R.drawable.snake),
                        contentDescription = "Head Icon",
                        modifier = Modifier.fillMaxSize(),

                    )
                }else{
                    Icon(
                        painter = painterResource(id = R.drawable.corpo),
                        contentDescription = "Body Icon",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color(4283126560)
                    )
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SnakePreview() {
    SnakeComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(4285563448)
        ) {
            SnakeScreen(SnakeViewModel())
        }
    }
}
