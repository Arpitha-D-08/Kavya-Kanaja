package com.example.kavyakanaja.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.kavyakanaja.data.FirebasePoemManager
import com.example.kavyakanaja.data.Poem
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PoemListScreen(

    onPoemClick: (Poem) -> Unit

) {

    var poems by remember {

        mutableStateOf<List<Poem>>(emptyList())
    }

    var searchQuery by remember {

        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        FirebasePoemManager.fetchPoems {

            poems = it
        }
    }

    val speechLauncher = rememberLauncherForActivityResult(

        contract = ActivityResultContracts.StartActivityForResult()

    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {

            val spokenText = result.data
                ?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )

            spokenText?.get(0)?.let {

                searchQuery = it
            }
        }
    }

    val filteredPoems = poems.filter {

        it.title.contains(searchQuery, ignoreCase = true) ||

                it.author.contains(searchQuery, ignoreCase = true) ||

                it.englishTitle.contains(searchQuery, ignoreCase = true) ||

                it.englishAuthor.contains(searchQuery, ignoreCase = true) ||

                it.category.contains(searchQuery, ignoreCase = true)
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {

        Text(

            text = "Kannada Poems",

            fontSize = 30.sp,

            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(

            text = "Explore beautiful Kannada literature ✨",

            fontSize = 16.sp,

            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(

            value = searchQuery,

            onValueChange = {

                searchQuery = it
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {

                Text("Search Poems")
            },

            leadingIcon = {

                Icon(

                    imageVector = Icons.Default.Search,

                    contentDescription = null
                )
            },

            trailingIcon = {

                Row {

                    IconButton(

                        onClick = {

                            val intent = Intent(
                                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
                            )

                            intent.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            )

                            intent.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE,
                                Locale.getDefault()
                            )

                            speechLauncher.launch(intent)
                        }
                    ) {

                        Text("🎤")
                    }

                    if (searchQuery.isNotEmpty()) {

                        IconButton(

                            onClick = {

                                searchQuery = ""
                            }
                        ) {

                            Icon(

                                imageVector = Icons.Default.Clear,

                                contentDescription = null
                            )
                        }
                    }
                }
            },

            shape = RoundedCornerShape(20.dp),

            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (filteredPoems.isEmpty()) {

            Box(

                modifier = Modifier.fillMaxSize(),

                contentAlignment = Alignment.Center
            ) {

                Column(

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(

                        text = "😔",

                        fontSize = 60.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(

                        text = "No poems found",

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(

                        text = "Try another keyword",

                        fontSize = 16.sp
                    )
                }
            }

        } else {

            LazyColumn {

                items(filteredPoems) { poem ->

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp)
                            .clickable {

                                onPoemClick(poem)
                            },

                        shape = RoundedCornerShape(24.dp),

                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp
                        )

                    ) {

                        Column {

                            Box {

                                Image(

                                    painter = rememberAsyncImagePainter(
                                        poem.poetImageUrl
                                    ),

                                    contentDescription = null,

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp),

                                    contentScale = ContentScale.Crop
                                )

                                Surface(

                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(12.dp),

                                    shape = RoundedCornerShape(20.dp),

                                    color = MaterialTheme.colorScheme.primaryContainer

                                ) {

                                    Text(

                                        text = poem.category,

                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        ),

                                        fontSize = 13.sp,

                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Column(

                                modifier = Modifier.padding(16.dp)

                            ) {

                                Text(

                                    text = poem.title,

                                    fontSize = 28.sp,

                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(

                                    text = poem.englishTitle,

                                    fontSize = 18.sp,

                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(
                                    modifier = Modifier.height(14.dp)
                                )

                                Text(

                                    text = "✍ ${poem.author}",

                                    fontSize = 20.sp,

                                    fontWeight = FontWeight.Medium
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(

                                    text = poem.englishAuthor,

                                    fontSize = 15.sp,

                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(
                                    modifier = Modifier.height(18.dp)
                                )

                                Row(

                                    modifier = Modifier.fillMaxWidth(),

                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    OutlinedButton(

                                        onClick = {

                                            onPoemClick(poem)
                                        },

                                        shape = RoundedCornerShape(14.dp)

                                    ) {

                                        Text("Tap to Read")
                                    }

                                    Button(

                                        onClick = {

                                            onPoemClick(poem)
                                        },

                                        shape = RoundedCornerShape(14.dp)

                                    ) {

                                        Text("Read Now")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}