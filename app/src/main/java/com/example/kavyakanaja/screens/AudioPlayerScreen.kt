package com.example.kavyakanaja.screens

import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kavyakanaja.data.RecentAudioManager
import java.util.Locale

@Composable
fun AudioPlayerScreen(

    onNavigateToPoet: (String) -> Unit,

    onCategoryClick: (String) -> Unit

) {

    val context = LocalContext.current

    var tts by remember {
        mutableStateOf<TextToSpeech?>(null)
    }

    var isSpeaking by remember {
        mutableStateOf(false)
    }

    var currentPlayingTitle by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        tts = TextToSpeech(context) { status ->

            if (status == TextToSpeech.SUCCESS) {

                tts?.language = Locale("kn", "IN")
                tts?.setSpeechRate(0.9f)
            }
        }
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
            .verticalScroll(rememberScrollState())
            .padding(16.dp)

    ) {

        Spacer(modifier = Modifier.height(8.dp))

        // FEATURE CARD

        Box(

            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4A00E0),
                            Color(0xFF8E2DE2)
                        )
                    )
                )
                .padding(24.dp)

        ) {

            Column(

                verticalArrangement = Arrangement.SpaceBetween

            ) {

                Text(

                    text = "Daily Featured Listening",

                    color = Color.White,

                    fontSize = 28.sp,

                    fontWeight = FontWeight.Bold
                )

                Button(

                    onClick = {

                        if (isSpeaking) {

                            tts?.stop()

                            isSpeaking = false

                        } else {

                            tts?.speak(
                                "ಕನ್ನಡ ಕವನಗಳ ವಾಚನ ಪ್ರಾರಂಭವಾಗಿದೆ",
                                QUEUE_FLUSH,
                                null,
                                null
                            )

                            isSpeaking = true
                        }
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),

                    shape = RoundedCornerShape(50.dp)

                ) {

                    Icon(

                        imageVector =
                            if (isSpeaking)
                                Icons.Default.Pause
                            else
                                Icons.Default.PlayArrow,

                        contentDescription = null,

                        tint = Color(0xFF5B2EFF)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(

                        text =
                            if (isSpeaking)
                                "Pause"
                            else
                                "Play Now",

                        color = Color(0xFF5B2EFF),

                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // RECENT AUDIOS

        Text(

            text = "Recent Poem Audios",

            fontSize = 28.sp,

            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (RecentAudioManager.recentAudios.isEmpty()) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp),

                contentAlignment = Alignment.Center

            ) {

                Text(

                    text = "No recent audio played",

                    color = Color.Gray,

                    fontSize = 18.sp
                )
            }

        } else {

            Column {

                RecentAudioManager.recentAudios.forEach { poem ->

                    AudioPoemCard(

                        title = poem.title,

                        subtitle = poem.author,

                        isPlaying = currentPlayingTitle == poem.title
                                && isSpeaking,

                        onPlayPause = {

                            if (
                                currentPlayingTitle == poem.title
                                && isSpeaking
                            ) {

                                tts?.stop()

                                isSpeaking = false

                            } else {

                                currentPlayingTitle = poem.title

                                tts?.speak(
                                    poem.content,
                                    QUEUE_FLUSH,
                                    null,
                                    null
                                )

                                isSpeaking = true
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun PoetCard(

    poetName: String,

    onClick: () -> Unit

) {

    Card(

        modifier = Modifier
            .padding(end = 14.dp)
            .size(130.dp)
            .clickable {

                onClick()
            },

        shape = RoundedCornerShape(24.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )

    ) {

        Column(

            modifier = Modifier.fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center

        ) {

            Icon(

                imageVector = Icons.Default.MusicNote,

                contentDescription = null,

                modifier = Modifier.size(52.dp),

                tint = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(

                text = poetName,

                fontSize = 20.sp,

                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun CategoryChip(

    title: String,

    onClick: () -> Unit

) {

    Surface(

        modifier = Modifier
            .padding(end = 12.dp)
            .clickable {

                onClick()
            },

        shape = RoundedCornerShape(18.dp),

        tonalElevation = 4.dp

    ) {

        Text(

            text = title,

            modifier = Modifier.padding(
                horizontal = 22.dp,
                vertical = 12.dp
            ),

            fontSize = 16.sp
        )
    }
}

@Composable
fun AudioPoemCard(

    title: String,

    subtitle: String,

    isPlaying: Boolean,

    onPlayPause: () -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(26.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )

    ) {

        Box(

            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4A00E0),
                            Color(0xFF8E2DE2)
                        )
                    )
                )
                .fillMaxWidth()
                .padding(20.dp)

        ) {

            Row(

                modifier = Modifier.fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement =
                    Arrangement.SpaceBetween

            ) {

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Icon(

                        imageVector = Icons.Default.MusicNote,

                        contentDescription = null,

                        tint = Color.White,

                        modifier = Modifier.size(42.dp)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(

                            text = title,

                            color = Color.White,

                            fontSize = 20.sp,

                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(

                            text = subtitle,

                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                IconButton(

                    onClick = {

                        onPlayPause()
                    }
                ) {

                    Icon(

                        imageVector =
                            if (isPlaying)
                                Icons.Default.Pause
                            else
                                Icons.Default.PlayArrow,

                        contentDescription = null,

                        tint = Color.White,

                        modifier = Modifier.size(34.dp)
                    )
                }
            }
        }
    }
}