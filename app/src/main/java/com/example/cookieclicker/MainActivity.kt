package com.example.cookieclicker

import ScoreboardViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cookieclicker.ui.theme.CookieClickerTheme

enum class Screen(@StringRes val title: Int){
    Main(title = R.string.app_name),
    Scoreboard(title = R.string.scoreboard)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookieClickerTheme {
                CookieClickerApp()
            }
        }
    }
}

@Composable
fun CookieClickerApp(
    navController: NavHostController = rememberNavController()
){
    // current destination in the navigation graph
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.Main.name
    )

    val viewModel: ScoreboardViewModel = viewModel()

    Scaffold(
        topBar = {
            CookieAppBar(
                currentScreen = currentScreen,
                onClick = {
                    if (currentScreen == Screen.Main) {
                        navController.navigate(Screen.Scoreboard.name)
                    } else {
                        navController.navigate(Screen.Main.name)
                    }
                }
            )
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Main.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = Screen.Main.name){
                MainScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    viewModel = viewModel
                )
            }
            composable(route = Screen.Scoreboard.name) {
                ScoreboardScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    viewModel = viewModel
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewApp(){
    CookieClickerTheme {
        CookieClickerApp()
    }
}