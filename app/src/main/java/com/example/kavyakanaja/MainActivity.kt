package com.example.kavyakanaja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.kavyakanaja.screens.LoginScreen
import com.example.kavyakanaja.screens.SplashScreen
import com.example.kavyakanaja.ui.theme.KavyaKanajaTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            KavyaKanajaTheme {

                Surface {

                    var currentScreen by remember {

                        mutableStateOf("splash")
                    }

                    var showExitDialog by remember {

                        mutableStateOf(false)
                    }

                    BackHandler(

                        enabled = currentScreen == "main"

                    ) {

                        showExitDialog = true
                    }

                    if (showExitDialog) {

                        AlertDialog(

                            onDismissRequest = {

                                showExitDialog = false
                            },

                            confirmButton = {

                                TextButton(

                                    onClick = {

                                        finish()
                                    }
                                ) {

                                    Text("Exit")
                                }
                            },

                            dismissButton = {

                                TextButton(

                                    onClick = {

                                        showExitDialog = false
                                    }
                                ) {

                                    Text("Cancel")
                                }
                            },

                            title = {

                                Text("Exit App")
                            },

                            text = {

                                Text(
                                    "Do you want to close Kavya Kanaja?"
                                )
                            }
                        )
                    }

                    when (currentScreen) {

                        "splash" -> {

                            SplashScreen(

                                onSplashFinished = {

                                    val currentUser =
                                        FirebaseAuth
                                            .getInstance()
                                            .currentUser

                                    currentScreen =

                                        if (currentUser != null) {

                                            "main"

                                        } else {

                                            "login"
                                        }
                                }
                            )
                        }

                        "login" -> {

                            LoginScreen(

                                onLoginClick = {

                                    currentScreen = "main"
                                }
                            )
                        }

                        "main" -> {

                            MainScreen(

                                navController = navController,

                                onLogout = {

                                    FirebaseAuth
                                        .getInstance()
                                        .signOut()

                                    currentScreen = "login"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}