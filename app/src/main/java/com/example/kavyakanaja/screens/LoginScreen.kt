package com.example.kavyakanaja.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kavyakanaja.data.UserSession
import com.example.kavyakanaja.ui.theme.CreamBg
import com.example.kavyakanaja.ui.theme.PurpleMain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

@Composable
fun LoginScreen(

    onLoginClick: () -> Unit

) {

    val context = LocalContext.current

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val auth = FirebaseAuth.getInstance()

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .verticalScroll(
                rememberScrollState()
            )
            .padding(24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center

    ) {

        Text(
            text = "ಕಾವ್ಯ ಕಣಜ",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleMain
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Kannada Poetry App",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(

            value = name,

            onValueChange = {
                name = it
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Name")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(

            value = email,

            onValueChange = {
                email = it
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Email")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(

            value = password,

            onValueChange = {
                password = it
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Password")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(

            onClick = {

                isLoading = true

                auth.createUserWithEmailAndPassword(

                    email,
                    password

                ).addOnCompleteListener {

                    if (it.isSuccessful) {

                        FirebaseAuth
                            .getInstance()
                            .currentUser
                            ?.updateProfile(

                                userProfileChangeRequest {

                                    displayName = name
                                }
                            )

                        UserSession.userName = name

                        isLoading = false

                        onLoginClick()

                    } else {

                        auth.signInWithEmailAndPassword(

                            email,
                            password

                        ).addOnCompleteListener { login ->

                            if (login.isSuccessful) {

                                UserSession.userName = name

                                isLoading = false

                                onLoginClick()

                            } else {

                                isLoading = false

                                errorMessage =
                                    "Authentication Failed"
                            }
                        }
                    }
                }
            },

            modifier = Modifier.fillMaxWidth()

        ) {

            Text("Login / Register")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(

            onClick = {

                if (email.isNotEmpty()) {

                    auth.sendPasswordResetEmail(

                        email

                    ).addOnCompleteListener {

                        if (it.isSuccessful) {

                            Toast.makeText(

                                context,

                                "Reset email sent",

                                Toast.LENGTH_SHORT

                            ).show()

                        } else {

                            Toast.makeText(

                                context,

                                "Failed to send reset email",

                                Toast.LENGTH_SHORT

                            ).show()
                        }
                    }

                } else {

                    Toast.makeText(

                        context,

                        "Enter email first",

                        Toast.LENGTH_SHORT

                    ).show()
                }
            }
        ) {

            Text("Forgot Password?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {

            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {

            Text(
                text = errorMessage,
                color = Color.Red
            )
        }
    }
}