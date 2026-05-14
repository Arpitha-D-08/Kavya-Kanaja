package com.example.kavyakanaja.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kavyakanaja.data.FavoritesManager
import com.example.kavyakanaja.data.GroqManager
import com.example.kavyakanaja.data.Poem
import com.example.kavyakanaja.data.RecentAudioManager
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun PoemDetailScreen(

    poem: Poem,

    onBackClick: () -> Unit

) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    var selectedWord by remember {
        mutableStateOf("")
    }

    var meaning by remember {
        mutableStateOf("")
    }

    var fontSize by remember {
        mutableStateOf(28.sp)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var isPlaying by remember {
        mutableStateOf(false)
    }

    var tts by remember {
        mutableStateOf<TextToSpeech?>(null)
    }

    LaunchedEffect(Unit) {

        tts = TextToSpeech(context) {

            if (it == TextToSpeech.SUCCESS) {

                tts?.language = Locale("kn")
            }
        }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)

    ) {

        Text(

            text = poem.title,

            fontSize = 30.sp,

            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(

            text = poem.meaning,

            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text = "✍ ${poem.author}",

            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row {

            Button(

                onClick = {

                    if (fontSize > 18.sp) {

                        fontSize =
                            (fontSize.value - 2).sp
                    }
                }

            ) {

                Text("A-")
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Button(

                onClick = {

                    if (fontSize < 40.sp) {

                        fontSize =
                            (fontSize.value + 2).sp
                    }
                }

            ) {

                Text("A+")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(

            text = "📖 Poem",

            fontSize = 32.sp,

            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        ClickableText(

            text = AnnotatedString(poem.content),

            style = TextStyle(

                fontSize = fontSize,

                lineHeight = 45.sp
            ),

            onClick = { offset ->

                val words =
                    poem.content.split(" ")

                var current = 0

                words.forEach { word ->

                    val start = current

                    val end =
                        current + word.length

                    if (offset in start..end) {

                        selectedWord = word

                        meaning = "Loading..."

                        showDialog = true

                        scope.launch {

                            try {

                                meaning =
                                    GroqManager.getResponse(
                                        "Translate this Kannada word to simple English meaning only: $word"
                                    )

                            } catch (e: Exception) {

                                meaning =
                                    "Translation failed"
                            }
                        }
                    }

                    current += word.length + 1
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(

            text = "Meaning",

            fontSize = 34.sp,

            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(

            text = poem.meaning,

            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // MODERN AUDIO PLAYER

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),

            shape = RoundedCornerShape(24.dp),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )

        ) {

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush =
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF3F51B5),
                                    Color(0xFF673AB7)
                                )
                            )
                    )
                    .padding(20.dp),

                verticalAlignment =
                    Alignment.CenterVertically,

                horizontalArrangement =
                    Arrangement.SpaceBetween

            ) {

                Column {

                    Text(

                        text =
                            if (isPlaying)
                                "🎵 Playing Poem..."
                            else
                                "🎧 Listen Poem",

                        color = Color.White,

                        fontSize = 22.sp,

                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(

                        text = poem.author,

                        color = Color.White.copy(
                            alpha = 0.8f
                        ),

                        fontSize = 16.sp
                    )
                }

                IconButton(

                    onClick = {

                        if (isPlaying) {

                            tts?.stop()

                            isPlaying = false

                        } else {

                            tts?.speak(
                                poem.content,
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                null
                            )

                            RecentAudioManager.addPoem(
                                poem
                            )

                            isPlaying = true
                        }
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

                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // FAVORITES

        Button(

            onClick = {

                FavoritesManager.addToFavorites(
                    context,
                    poem
                )

                Toast.makeText(
                    context,
                    "Added to Favorites",
                    Toast.LENGTH_SHORT
                ).show()
            },

            modifier = Modifier.fillMaxWidth()

        ) {

            Text(

                text = "❤️ Add to Favorites",

                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // SHARE

        Button(

            onClick = {

                val shareIntent =
                    Intent().apply {

                        action =
                            Intent.ACTION_SEND

                        putExtra(
                            Intent.EXTRA_TEXT,
                            poem.content
                        )

                        type = "text/plain"
                    }

                context.startActivity(

                    Intent.createChooser(
                        shareIntent,
                        "Share Poem"
                    )
                )
            },

            modifier = Modifier.fillMaxWidth()

        ) {

            Text(

                text = "📤 Share Poem",

                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // COPY

        Button(

            onClick = {

                val clipboard =
                    context.getSystemService(
                        Context.CLIPBOARD_SERVICE
                    ) as ClipboardManager

                val clip =
                    ClipData.newPlainText(
                        "Poem",
                        poem.content
                    )

                clipboard.setPrimaryClip(clip)

                Toast.makeText(
                    context,
                    "Poem Copied",
                    Toast.LENGTH_SHORT
                ).show()
            },

            modifier = Modifier.fillMaxWidth()

        ) {

            Text(

                text = "📋 Copy Poem",

                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // BACK

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

        Spacer(modifier = Modifier.height(20.dp))
    }

    // WORD MEANING DIALOG

    if (showDialog) {

        AlertDialog(

            onDismissRequest = {

                showDialog = false
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        showDialog = false
                    }

                ) {

                    Text("OK")
                }
            },

            title = {

                Text("Word Meaning")
            },

            text = {

                Column {

                    Text(
                        text =
                            "Kannada Word: $selectedWord"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Meaning: $meaning"
                    )
                }
            }
        )
    }
}