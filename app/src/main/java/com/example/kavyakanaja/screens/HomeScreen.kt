package com.example.kavyakanaja.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kavyakanaja.data.FavoritesManager
import com.example.kavyakanaja.data.JsonHelper
import com.example.kavyakanaja.data.RecentManager
import com.example.kavyakanaja.data.SearchHistoryManager
import com.example.kavyakanaja.ui.theme.CreamBg
import com.example.kavyakanaja.ui.theme.PurpleMain
import com.example.kavyakanaja.ui.theme.SoftCard
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

@Composable
fun HomeScreen(

    onNavigate: (Int) -> Unit

) {

    val context = LocalContext.current

    var searchText by remember {

        mutableStateOf("")
    }

    val userName =

        FirebaseAuth
            .getInstance()
            .currentUser
            ?.email
            ?.substringBefore("@")
            ?: "User"

    val allPoems =
        JsonHelper.loadPoems(context)

    val poems by remember(

        searchText

    ) {

        mutableStateOf(

            if (searchText.isBlank()) {

                allPoems

            } else {

                allPoems.filter { poem ->

                    poem.title.lowercase()
                        .contains(
                            searchText.lowercase()
                        )

                            ||

                            poem.author.lowercase()
                                .contains(
                                    searchText.lowercase()
                                )

                            ||

                            poem.englishTitle.lowercase()
                                .contains(
                                    searchText.lowercase()
                                )

                            ||

                            poem.englishAuthor.lowercase()
                                .contains(
                                    searchText.lowercase()
                                )

                            ||

                            poem.content.lowercase()
                                .contains(
                                    searchText.lowercase()
                                )

                            ||

                            poem.meaning.lowercase()
                                .contains(
                                    searchText.lowercase()
                                )
                }
            }
        )
    }
    val searchResults =

        if (searchText.isBlank()) {

            emptyList()

        } else {

            poems.take(5)
        }

    val calendar = Calendar.getInstance()

    val hour =
        calendar.get(Calendar.HOUR_OF_DAY)

    val greeting = when {

        hour < 12 ->
            "☀ Good Morning"

        hour < 17 ->
            "🌤 Good Afternoon"

        else ->
            "🌙 Good Evening"
    }

    val day =
        calendar.get(Calendar.DAY_OF_YEAR)

    val poemOfTheDay =

        if (poems.isNotEmpty()) {

            poems[day % poems.size]

        } else {

            allPoems[0]
        }

    val categories = listOf(

        "Nature 🌿",

        "Bhakti 🙏",

        "Motivational 🔥",

        "Patriotic 🇮🇳"
    )

    val quotes = listOf(

        "Poetry is the voice of the soul ✨",

        "Kannada literature is timeless 📖",

        "Words create worlds 🌍",

        "Reading poems inspires life 💜"
    )

    val randomQuote =
        quotes.random()

    AnimatedVisibility(

        visible = true,

        enter = fadeIn() +
                slideInVertically()

    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(CreamBg)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {

            Text(
                text = "ಕಾವ್ಯ ಕಣಜ",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleMain
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "$greeting, $userName 👋",
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            OutlinedTextField(

                value = searchText,

                onValueChange = {

                    searchText = it

                    SearchHistoryManager
                        .addSearch(it)
                },

                label = {

                    Text("Search Poems")
                },

                trailingIcon = {

                    if (searchText.isNotEmpty()) {

                        TextButton(

                            onClick = {

                                searchText = ""
                            }

                        ) {

                            Text("❌")
                        }
                    }
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (searchResults.isNotEmpty()) {

                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = SoftCard
                    ),

                    modifier = Modifier.fillMaxWidth()

                ) {

                    Column(

                        modifier = Modifier.padding(12.dp)

                    ) {

                        searchResults.forEach { poem ->

                            AssistChip(

                                onClick = {

                                    searchText = poem.title
                                },

                                label = {

                                    Text(
                                        "${poem.title} - ${poem.author}"
                                    )
                                }
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }

            if (

                searchText.isNotBlank()

                &&

                poems.isEmpty()

            ) {

                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = SoftCard
                    ),

                    modifier = Modifier.fillMaxWidth()

                ) {

                    Column(

                        modifier = Modifier.padding(20.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally

                    ) {

                        Text(
                            text = "😔",
                            fontSize = 50.sp
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = "No poems found",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = PurpleMain
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Try another keyword"
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }

            if (

                SearchHistoryManager
                    .searchHistory
                    .isNotEmpty()

            ) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Recent Searches",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    TextButton(
                        onClick = {
                            SearchHistoryManager.clearSearchHistory()
                        }
                    ) {
                        Text("Clear")
                    }
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                LazyRow(

                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)

                ) {

                    items(

                        SearchHistoryManager
                            .searchHistory

                    ) { history ->

                        AssistChip(

                            onClick = {

                                searchText = history
                            },

                            label = {

                                Text(history)
                            }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }

            Text(
                text = "Kannada Literary Revival",
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)

            ) {

                Card(

                    shape = RoundedCornerShape(24.dp),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),

                    colors = CardDefaults.cardColors(
                        containerColor = SoftCard
                    ),

                    modifier = Modifier.weight(1f)

                ) {

                    Column(

                        modifier = Modifier.padding(16.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally

                    ) {

                        Text(
                            text = "❤️",
                            fontSize = 28.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                "${FavoritesManager.favoritePoems.size}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = PurpleMain
                        )

                        Text(
                            text = "Favorites"
                        )
                    }
                }

                Card(

                    shape = RoundedCornerShape(24.dp),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),

                    colors = CardDefaults.cardColors(
                        containerColor = SoftCard
                    ),

                    modifier = Modifier.weight(1f)

                ) {

                    Column(

                        modifier = Modifier.padding(16.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally

                    ) {

                        Text(
                            text = "📖",
                            fontSize = 28.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "100+",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = PurpleMain
                        )

                        Text(
                            text = "Poems"
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Card(

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = SoftCard
                ),

                modifier = Modifier.fillMaxWidth()

            ) {

                Column(

                    modifier = Modifier.padding(24.dp)

                ) {

                    Text(
                        text = "✨ Poem of the Day",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurpleMain
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text(
                        text = poemOfTheDay.title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurpleMain
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = poemOfTheDay.author,
                        fontSize = 18.sp
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text(
                        text = poemOfTheDay.content,
                        fontSize = 18.sp
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text(
                        text = poemOfTheDay.meaning,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Card(

                colors = CardDefaults.cardColors(
                    containerColor = SoftCard
                ),

                modifier = Modifier.fillMaxWidth()

            ) {

                Column(

                    modifier = Modifier.padding(20.dp)

                ) {

                    Text(
                        text = "💡 Daily Inspiration",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurpleMain
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = randomQuote,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Text(
                text = "Categories",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleMain
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LazyVerticalGrid(

                columns = GridCells.Fixed(2),

                modifier = Modifier.height(220.dp),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)

            ) {

                items(categories) { category ->

                    Card(

                        onClick = {

                            searchText =
                                category.substringBefore(" ")
                        },

                        colors = CardDefaults.cardColors(
                            containerColor = SoftCard
                        )

                    ) {

                        Column(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),

                            horizontalAlignment =
                                Alignment.CenterHorizontally

                        ) {

                            Text(
                                text = category,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurpleMain
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Text(
                text = "🕘 Recently Viewed",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleMain
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LazyRow(

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)

            ) {

                items(RecentManager.getRecentPoems()) { poem ->

                    Card(

                        onClick = {

                            searchText = poem.title

                            onNavigate(1)
                        },

                        colors = CardDefaults.cardColors(
                            containerColor = SoftCard
                        ),

                        modifier = Modifier.width(220.dp)

                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = poem.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurpleMain
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = poem.author
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}