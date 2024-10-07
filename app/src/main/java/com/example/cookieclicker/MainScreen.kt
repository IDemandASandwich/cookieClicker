package com.example.cookieclicker

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookieclicker.ui.theme.CookieClickerTheme

@Preview
@Composable
fun PreviewMainScreen(modifier: Modifier = Modifier){
    CookieClickerTheme {
        MainScreen(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
){
    var cookieAmount by remember { mutableIntStateOf(0) }
    var timerRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableDoubleStateOf(10.0) }
    var isClickable by remember { mutableStateOf(true) }
    var username by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (!timerRunning) {
                    cookieAmount = 0
                    timerRunning = true
                    startTimer(
                        time = 10000L,
                        onTimerTick = { time -> timeLeft = time },
                        onTimerFinish = {
                            isClickable = false
                            startTimer(
                                time = 2000L,
                                onTimerFinish = {
                                    timerRunning = false
                                    isClickable = true
                                }
                            )
                        }
                    )
                } else if (isClickable) {
                    cookieAmount++
                }
            }
    ){
        // background
        Box(
            modifier = Modifier
                .background(color = Color.Black)
                .fillMaxSize()
                .paint(
                    painterResource(R.drawable.bakery),
                    contentScale = ContentScale.FillBounds,
                    alpha = 0.4f
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                    .padding(top = 20.dp)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier
                    .width(100.dp)
                    .padding(bottom = 20.dp)
            )

            Text(
                text = stringResource(R.string.timer, "%.2f".format(timeLeft)),
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                color = Color.White,

            )
        }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.cookie),
                contentDescription = stringResource(R.string.cookie_image_txt),
                modifier = Modifier
                    .size((100 + cookieAmount * 2.2).dp)
            )
            Text(
                text = stringResource(R.string.cookies_amount, cookieAmount),
                color = Color.White
            )
        }
    }
}

private fun startTimer(time: Long, onTimerTick: (Double) -> Unit = {}, onTimerFinish: () -> Unit) {
    object : CountDownTimer(time, 10) {
        override fun onTick(millisUntilFinished: Long) {
            val timeLeft = millisUntilFinished / 1000.0
            onTimerTick(timeLeft)
        }

        override fun onFinish() {
            onTimerTick(0.0)
            onTimerFinish()
        }
    }.start()
}