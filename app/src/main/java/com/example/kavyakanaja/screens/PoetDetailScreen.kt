package com.example.kavyakanaja.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kavyakanaja.data.GroqManager
import java.util.Locale

@Composable
fun PoetDetailScreen(

    poetName: String,

    onBackClick: () -> Unit

) {

    val context = LocalContext.current

    var poetInfo by remember {

        mutableStateOf("Loading...")
    }

    var isLoading by remember {

        mutableStateOf(true)
    }

    var tts by remember {

        mutableStateOf<TextToSpeech?>(null)
    }

    LaunchedEffect(Unit) {

        tts = TextToSpeech(context) {

            if (it == TextToSpeech.SUCCESS) {

                tts?.language = Locale.US
            }
        }

        try {

            poetInfo = GroqManager.getResponse(

                "Give short biography, achievements, awards and famous works of Kannada poet $poetName in simple points"
            )

        } catch (e: Exception) {

            poetInfo = "Failed to load poet information"
        }

        isLoading = false
    }

    DisposableEffect(Unit) {

        onDispose {

            tts?.stop()
            tts?.shutdown()
        }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(18.dp)

    ) {

        Text(

            text = poetName,

            fontSize = 34.sp,

            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (isLoading) {

            Column {

                CircularProgressIndicator()

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(

                    text = "Loading poet details...",

                    fontSize = 18.sp
                )
            }

        } else {

            Card(

                modifier = Modifier.fillMaxWidth()

            ) {

                Text(

                    text = poetInfo,

                    modifier = Modifier.padding(20.dp),

                    fontSize = 20.sp,

                    lineHeight = 34.sp
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                Button(

                    onClick = {

                        tts?.speak(

                            poetInfo,

                            TextToSpeech.QUEUE_FLUSH,

                            null,

                            null
                        )
                    },

                    modifier = Modifier.weight(1f)

                ) {

                    Text(

                        text = "🔊 Listen",

                        fontSize = 16.sp
                    )
                }

                OutlinedButton(

                    onClick = {

                        tts?.stop()
                    },

                    modifier = Modifier.weight(1f)

                ) {

                    Text(

                        text = "⏹ Stop",

                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Button(

            onClick = {

                onBackClick()
            },

            modifier = Modifier.fillMaxWidth()

        ) {

            Text(

                text = "⬅ Back",

                fontSize = 18.sp
            )
        }
    }
}