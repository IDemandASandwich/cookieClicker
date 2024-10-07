package com.example.cookieclicker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cookieclicker.ui.theme.CookieClickerTheme

@Composable
fun ScoreboardScreen(
    modifier: Modifier = Modifier
){
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ){

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