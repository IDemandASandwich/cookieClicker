package com.example.cookieclicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookieclicker.ui.theme.CookieClickerTheme
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ScoreboardScreen(
    modifier: Modifier = Modifier,
    viewModel: ScoreboardViewModel = viewModel()
){
    Surface(
        modifier = modifier
            .wrapContentSize(align = Alignment.TopCenter)
            .fillMaxSize()
    ){
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(viewModel.scores) { score ->
                Text(text = "${score.first} : ${score.second}")
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.BottomCenter
    ){
        Button(
            content = {Text("Clear scoreboard")},
            onClick = { viewModel.clearScore() }
        )
    }
}

@Preview
@Composable
fun PreviewScoreboard(modifier: Modifier = Modifier){
    CookieClickerTheme{
        ScoreboardScreen(

        )
    }
}