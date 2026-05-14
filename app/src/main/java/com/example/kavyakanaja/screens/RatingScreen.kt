package com.example.kavyakanaja.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RatingScreen() {

    val context = LocalContext.current

    var rating by remember {
        mutableStateOf(0)
    }

    var feedback by remember {
        mutableStateOf("")
    }

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

                        text = "⭐ Rate Kavya Kanaja",

                        fontSize = 30.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(

                        text = "Share your experience with us",

                        fontSize = 18.sp,

                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(25.dp),

            colors = CardDefaults.cardColors(

                containerColor = Color(0xFFF7F3FF)
            )
        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(

                    text = "How do you like the app?",

                    fontSize = 24.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(25.dp))

                Row(

                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    for (i in 1..5) {

                        Icon(

                            imageVector =

                                if (i <= rating)
                                    Icons.Filled.Star

                                else
                                    Icons.Outlined.StarBorder,

                            contentDescription = null,

                            tint =

                                if (i <= rating)
                                    Color(0xFFFFC107)

                                else
                                    Color.Gray,

                            modifier = Modifier
                                .size(42.dp)
                                .noRippleClickable {

                                    rating = i
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(

                    value = feedback,

                    onValueChange = {

                        feedback = it
                    },

                    label = {

                        Text("Write feedback")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),

                    shape = RoundedCornerShape(18.dp)
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(

                    onClick = {

                        Toast.makeText(

                            context,

                            "Thanks for your feedback ⭐",

                            Toast.LENGTH_SHORT

                        ).show()

                        feedback = ""
                        rating = 0
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

                        text = "Submit Rating",

                        fontSize = 20.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(

                containerColor = Color.White
            )
        ) {

            Column(

                modifier = Modifier.padding(22.dp)
            ) {

                Text(

                    text = "💜 Why Ratings Matter",

                    fontSize = 26.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(

                    text =

                        "• Helps improve the application\n\n" +

                                "• Encourages Kannada literature preservation\n\n" +

                                "• Supports future updates\n\n" +

                                "• Motivates the developer",

                    fontSize = 18.sp,

                    lineHeight = 32.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

fun Modifier.noRippleClickable(
    onClick: () -> Unit
): Modifier = composed {

    clickable(

        indication = null,

        interactionSource = remember {

            MutableInteractionSource()
        }
    ) {

        onClick()
    }
}