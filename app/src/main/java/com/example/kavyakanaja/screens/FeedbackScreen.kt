package com.example.kavyakanaja.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FeedbackScreen() {

    val context = LocalContext.current

    var selectedOption by remember {
        mutableStateOf("Feature Request")
    }

    var message by remember {
        mutableStateOf("")
    }

    val options = listOf(
        "Feature Request",
        "Bug Report",
        "Suggest Poem",
        "General Support"
    )

    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(30.dp)
        ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(

                        brush = Brush.linearGradient(

                            colors = listOf(
                                Color(0xFF6A11CB),
                                Color(0xFF8E2DE2)
                            )
                        )
                    )
                    .padding(30.dp)
            ) {

                Column(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(

                        text = "💡 Suggestions & Support",

                        fontSize = 28.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(

                        text = "Help us improve Kavya Kanaja",

                        fontSize = 18.sp,

                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF7F3FF)
            )
        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {

                Text(

                    text = "Select Category",

                    fontSize = 22.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                options.forEach { option ->

                    Row(

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(

                            selected = selectedOption == option,

                            onClick = {

                                selectedOption = option
                            }
                        )

                        Text(

                            text = option,

                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(

                    value = message,

                    onValueChange = {

                        message = it
                    },

                    label = {

                        Text("Write here")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),

                    shape = RoundedCornerShape(18.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Button(

                    onClick = {

                        Toast.makeText(

                            context,

                            "Submitted Successfully 💜",

                            Toast.LENGTH_SHORT

                        ).show()

                        message = ""
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape = RoundedCornerShape(18.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A43EC)
                    )
                ) {

                    Text(

                        text = "Submit",

                        fontSize = 20.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(22.dp)
        ) {

            Column(

                modifier = Modifier.padding(22.dp)
            ) {

                Text(

                    text = "👩 Developer Contact",

                    fontSize = 24.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Developer: Arpitha",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Project: Kavya Kanaja",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Platform: Android",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(22.dp)
        ) {

            Column(

                modifier = Modifier.padding(22.dp)
            ) {

                Text(

                    text = "📱 Application Info",

                    fontSize = 24.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Version: 1.0",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Language: Kotlin",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "UI: Jetpack Compose",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Database: Firebase",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEEE5FF)
            )
        ) {

            Column(

                modifier = Modifier.padding(24.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(

                    text = "💜 Thank You",

                    fontSize = 28.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(

                    text =
                        "Your support and suggestions help preserve Kannada literature digitally.",

                    fontSize = 18.sp,

                    lineHeight = 30.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}