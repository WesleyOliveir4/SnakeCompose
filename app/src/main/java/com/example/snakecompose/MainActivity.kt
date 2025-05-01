package com.example.snakecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.snakecompose.ui.theme.SnakeComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val game: Game = Game(lifecycleScope)

        setContent {
            SnakeComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(4285563448)
                ) {
                    Snake(game)
                }
            }
        }
    }
}

data class State(val food: Pair<Int, Int>, val snake: List<Pair<Int, Int>>)

class Game(private val scope: CoroutineScope){

    private val mutex = Mutex()
    private val mutableState = MutableStateFlow(State(food = Pair(5, 5), snake = listOf(Pair(7, 7))))
    val state: Flow<State> = mutableState

    private val mutablePoints = MutableStateFlow(0)
    val points: Flow<Int> = mutablePoints

    var move = MutableStateFlow(Pair(1, 0))
        set(value){
            scope.launch {
                mutex.withLock {
                     value
                }
            }
        }

    init {
        scope.launch {
            var snakeLength = 4

            while (true){
                delay(150)
                mutableState.update {
                    var newPosition = it.snake.first().let { position ->

                    mutex.withLock {
                            Pair(
                                (position.first + move.value.first + BOARD_SIZE) % BOARD_SIZE,
                                (position.second + move.value.second+ BOARD_SIZE) % BOARD_SIZE
                            )

                        }
                    }

                    if (newPosition == it.food){
                        increasePoints()
                        snakeLength++
                    }

                    if (it.snake.contains(newPosition)){
                        snakeLength = 4
                    }

                    it.copy(
                        food = if (newPosition == it.food) {
                            Pair(
                                Random.nextInt(BOARD_SIZE),
                                Random.nextInt(BOARD_SIZE))
                        } else {
                            it.food
                        },
                        snake = listOf(newPosition) + it.snake.take(snakeLength - 1)
                    )
                }
            }
        }
    }

    fun increasePoints(){
        scope.launch{
            mutex.withLock {
                mutablePoints.update {
                    it + 1
                }
            }
        }
    }

    companion object{
        const val BOARD_SIZE = 16
    }
}

@Composable
fun Snake(game: Game) {
    val state  = game.state.collectAsState(initial = null)
    val points = game.points.collectAsState(initial = 0)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        state.value?.let {
         Board(it)
        }
        scoreboard(points.value)
        Buttons{
            game.move.value = it
        }
    }
}
@Composable
fun scoreboard(points: Int = 0) {
        Box(
            Modifier
                .size(width = 100.dp, height = 60.dp)
                .border(2.dp, Color(4283126560)),
        ){
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

        Button(onClick = { onDirectionChange(Pair(0,-1)) }, modifier = buttonsSize,colors = buttonsColor) {
            Icon(Icons.Default.KeyboardArrowUp, null)
        }
        Row{
            Button(onClick = { onDirectionChange(Pair(-1, 0)) }, modifier = buttonsSize, colors = buttonsColor) {
                Icon(Icons.Default.KeyboardArrowLeft, null)
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { onDirectionChange(Pair(1, 0)) }, modifier = buttonsSize, colors = buttonsColor) {
                Icon(Icons.Default.KeyboardArrowRight, null)
            }
        }

        Button(onClick = { onDirectionChange(Pair(0,1)) }, modifier = buttonsSize, colors = buttonsColor) {
            Icon(Icons.Default.KeyboardArrowDown, null)
        }
    }
}


@Composable
fun Board(state: State) {
    BoxWithConstraints(Modifier.padding(16.dp)) {
        val tileSize = maxWidth / Game.BOARD_SIZE
        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, Color(4283126560))
        )

        Box(
            Modifier.offset(
                x = tileSize * state.food.first,
                y = tileSize * state.food.second
            ).size(tileSize).background(Color(4283126560), CircleShape)
        )

        state.snake.forEach {
            Box(
                Modifier.offset(
                    x = tileSize * it.first,
                    y = tileSize * it.second
                ).size(tileSize).background(Color(4283126560), Small)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SnakePreview() {
     val scope = CoroutineScope(Dispatchers.Default)
    SnakeComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(4285563448)
        ){
            Snake(Game(scope))
        }
    }
}