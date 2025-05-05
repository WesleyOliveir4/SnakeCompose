package com.example.snakecompose.ui.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
    fun HomeSnakeGameScreen(
        navigateToGameScreen: () -> Unit = {}
    ){
        SnakeComposeTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                HomeSnakeGameContent(navigateToGameScreen)
            }
        }
    }

    @Composable
    fun HomeSnakeGameContent(navigateToGameScreen: () -> Unit) {
        
        Scaffold(){ paddinValues ->
            Column(
                modifier = Modifier
                    .consumeWindowInsets(paddinValues)
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 85.dp),
                    text = "Snake Game",
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                )


                Button(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 15.dp),
                    onClick = {
                        navigateToGameScreen()
                    }
                ){
                    Text(text = "Start Game")
                }
            }

        }

    }



    @Preview(showBackground = true)
    @Composable
    fun GameOverPreview(){
        SnakeComposeTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ){
                HomeSnakeGameScreen()
            }
        }
    }