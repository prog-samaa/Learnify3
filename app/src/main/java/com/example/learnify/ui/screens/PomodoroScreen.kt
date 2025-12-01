package com.example.learnify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnify.R
import com.example.learnify.ui.PomodoroViewModel
import com.example.learnify.ui.theme.AppBackgroundColor
import com.example.learnify.ui.theme.Light_Brown
import com.example.learnify.ui.theme.PrimaryColor

@Composable
fun PomodoroScreen(navController: NavController, viewModel: PomodoroViewModel) {
    val timeLeft = viewModel.timeLeft
    val totalTime = viewModel.totalTime
    val isBreak = viewModel.isBreakTime

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)
    val progress = if (totalTime > 0) (timeLeft.toFloat() / totalTime.toFloat()) else 0f

    val titleText = if (isBreak) "Break Time" else "Work Time"
    val titleColor = Light_Brown
    val buttonColor = PrimaryColor
    val titleFont = FontFamily(Font(R.font.playwrite))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(
                        id = if (isBreak) R.drawable.break_logo else R.drawable.work_logo
                    ),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(56.dp)
                        .padding(end = 6.dp)
                )
                Text(
                    text = titleText,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = titleColor,
                    fontFamily = titleFont,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(200.dp)
            ) {
                CircularProgressIndicator(
                    progress = 1f - progress,
                    strokeWidth = 8.dp,
                    color = PrimaryColor,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = formattedTime,
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (viewModel.isRunning) viewModel.pauseTimer()
                        else viewModel.startTimer()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .width(140.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = if (viewModel.isRunning) "Pause" else "Start",
                        fontSize = 18.sp
                    )
                }
                Button(
                    onClick = { viewModel.resetTimer() },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp)
                ) {
                    Text("Reset", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.startWork() },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .width(140.dp)
                        .height(50.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            "25m Work",
                            fontSize = 16.sp,
                            color = titleColor,
                            fontFamily = titleFont,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Button(
                    onClick = { viewModel.startBreak() },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .width(140.dp)
                        .height(50.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            "5m Break",
                            fontSize = 16.sp,
                            color = titleColor,
                            fontFamily = titleFont,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
