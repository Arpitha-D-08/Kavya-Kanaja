package com.example.kavyakanaja.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kavyakanaja.data.FavoritesManager
import com.example.kavyakanaja.data.Poem
import com.example.kavyakanaja.ui.theme.CreamBg
import com.example.kavyakanaja.ui.theme.PurpleMain
import com.example.kavyakanaja.ui.theme.SoftCard

@Composable
fun FavoritesScreen() {

    val context = LocalContext.current

    LaunchedEffect(Unit) {

        FavoritesManager
            .loadFavorites(context)
    }

    val favoritePoems =
        FavoritesManager.favoritePoems

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .padding(16.dp)

    ) {

        Text(
            text = "Favorite Poems",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleMain
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(

            onClick = {

                FavoritesManager.clearFavorites(
                    context
                )
            }

        ) {

            Text("🗑 Clear Favorites")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (favoritePoems.isEmpty()) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally

            ) {

                Text(
                    text = "💜",
                    fontSize = 70.sp
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "No favorites yet",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurpleMain
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text =
                        "Save your favorite poems here"
                )
            }

        } else {

            LazyColumn {

                items(favoritePoems) { poem ->

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = SoftCard
                        )

                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = poem.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurpleMain
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = poem.author,
                                color = Color.Black
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = poem.content,
                                color = Color.Black
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            OutlinedButton(

                                onClick = {

                                    FavoritesManager
                                        .removeFavorite(

                                            context,

                                            poem
                                        )
                                }

                            ) {

                                Text("❌ Remove")
                            }
                        }
                    }
                }
            }
        }
    }
}