package com.example.kavyakanaja.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(

    onSplashFinished: () -> Unit

) {

    var startAnimation by remember {

        mutableStateOf(false)
    }

    val scale by animateFloatAsState(

        targetValue =

            if (startAnimation) 1f
            else 0.6f,

        animationSpec = tween(
            durationMillis = 1200
        ),

        label = ""
    )

    val alpha by animateFloatAsState(

        targetValue =

            if (startAnimation) 1f
            else 0f,

        animationSpec = tween(
            durationMillis = 1200
        ),

        label = ""
    )

    LaunchedEffect(Unit) {

        startAnimation = true

        delay(2200)

        onSplashFinished()
    }

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),

        contentAlignment =
            Alignment.Center

    ) {

        Column(

            horizontalAlignment =
                Alignment.CenterHorizontally,

            modifier = Modifier
                .scale(scale)
                .alpha(alpha)

        ) {

            Text(
                text = "📖",
                fontSize = 90.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "ಕಾವ್ಯ ಕಣಜ",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Kannada Literary Revival",
                fontSize = 18.sp
            )
        }
    }
}