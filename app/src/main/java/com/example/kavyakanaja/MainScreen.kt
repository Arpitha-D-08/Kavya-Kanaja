package com.example.kavyakanaja

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kavyakanaja.data.Poem
import com.example.kavyakanaja.data.RecentManager
import com.example.kavyakanaja.screens.*
import com.example.kavyakanaja.ui.screens.ChatBotScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(

    navController: NavHostController,

    onLogout: () -> Unit

) {

    var selectedItem by remember {

        mutableStateOf(0)
    }

    var selectedPoem by remember {

        mutableStateOf<Poem?>(null)
    }

    var showPoemDetail by remember {

        mutableStateOf(false)
    }

    var selectedPoet by remember {

        mutableStateOf("")
    }

    var showPoetDetail by remember {

        mutableStateOf(false)
    }

    var showLogoutDialog by remember {

        mutableStateOf(false)
    }

    BackHandler(

        enabled = !showPoemDetail && !showPoetDetail

    ) {

        showLogoutDialog = true
    }

    if (showLogoutDialog) {

        AlertDialog(

            onDismissRequest = {

                showLogoutDialog = false
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        showLogoutDialog = false

                        onLogout()
                    }
                ) {

                    Text("Logout")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showLogoutDialog = false
                    }
                ) {

                    Text("Cancel")
                }
            },

            title = {

                Text("Logout")
            },

            text = {

                Text(
                    "Are you sure you want to logout?"
                )
            }
        )
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("Kavya Kanaja")
                },

                actions = {

                    Button(

                        onClick = {

                            showLogoutDialog = true
                        }

                    ) {

                        Text("Logout")
                    }
                }
            )
        },

        bottomBar = {

            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp,
                        vertical = 10.dp
                    ),

                shape = RoundedCornerShape(30.dp),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(
                            rememberScrollState()
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 10.dp
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    ModernBottomNavItem(
                        icon = "🏠",
                        label = "Home",
                        selected = selectedItem == 0
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 0
                    }

                    ModernBottomNavItem(
                        icon = "📖",
                        label = "Poems",
                        selected = selectedItem == 1
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 1
                    }

                    ModernBottomNavItem(
                        icon = "❤️",
                        label = "Favorites",
                        selected = selectedItem == 2
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 2
                    }

                    ModernBottomNavItem(
                        icon = "🎧",
                        label = "Audio",
                        selected = selectedItem == 3
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 3
                    }

                    ModernBottomNavItem(
                        icon = "👤",
                        label = "Poets",
                        selected = selectedItem == 4
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 4
                    }

                    ModernBottomNavItem(
                        icon = "🤖",
                        label = "AI",
                        selected = selectedItem == 5
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 5
                    }

                    ModernBottomNavItem(
                        icon = "🧑",
                        label = "Profile",
                        selected = selectedItem == 6
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 6
                    }

                    ModernBottomNavItem(
                        icon = "ℹ️",
                        label = "About",
                        selected = selectedItem == 7
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 7
                    }

                    ModernBottomNavItem(
                        icon = "⭐",
                        label = "Rating",
                        selected = selectedItem == 8
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 8
                    }

                    ModernBottomNavItem(
                        icon = "🕒",
                        label = "Recent",
                        selected = selectedItem == 9
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 9
                    }

                    ModernBottomNavItem(
                        icon = "💡",
                        label = "Support",
                        selected = selectedItem == 10
                    ) {

                        showPoemDetail = false
                        showPoetDetail = false

                        selectedItem = 10
                    }
                }
            }
        }
    ) { paddingValues ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

        ) {

            if (showPoemDetail) {

                selectedPoem?.let {

                    PoemDetailScreen(

                        poem = it,

                        onBackClick = {

                            showPoemDetail = false
                        }
                    )
                }

            } else if (showPoetDetail) {

                PoetDetailScreen(

                    poetName = selectedPoet,

                    onBackClick = {

                        showPoetDetail = false
                    }
                )

            } else {

                when (selectedItem) {

                    0 -> HomeScreen(

                        onNavigate = {

                            selectedItem = it
                        }
                    )

                    1 -> PoemListScreen(

                        onPoemClick = { poem ->

                            selectedPoem = poem

                            RecentManager.addPoem(poem)

                            showPoemDetail = true
                        }
                    )

                    2 -> FavoritesScreen()

                    3 -> AudioPlayerScreen(

                        onNavigateToPoet = {

                            selectedItem = 4
                        },

                        onCategoryClick = {

                            selectedItem = 1
                        }
                    )

                    4 -> PoetListScreen(

                        onPoetClick = { poet ->

                            selectedPoet = poet.name

                            showPoetDetail = true
                        }
                    )

                    5 -> ChatBotScreen()

                    6 -> ProfileScreen()

                    7 -> AboutScreen()

                    8 -> RatingScreen()

                    9 -> RecentScreen()

                    10 -> FeedbackScreen()
                }
            }
        }
    }
}

@Composable
fun BottomNavItem(

    icon: String,

    label: String,

    onClick: () -> Unit

) {

    Column(

        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clickable {

                onClick()
            },

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(text = icon)

        Text(text = label)
    }
}
@Composable
fun ModernBottomNavItem(
    icon: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .padding(horizontal = 6.dp)
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(

            containerColor =

                if (selected)
                    Color(0xFF6A11CB)

                else
                    Color.Transparent
        )
    ) {

        Column(

            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = icon,
                fontSize = 22.sp
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(

                text = label,

                color =

                    if (selected)
                        Color.White

                    else
                        Color.Gray,

                fontSize = 13.sp
            )
        }
    }
}
