package com.example.kavyakanaja.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen() {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Card(
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
                    .padding(28.dp)
            ) {

                Column {

                    Text(
                        text = "Kavya Kanaja ✨",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Preserving Kannada literature digitally through poems, audio and AI.",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF8F3FF)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "📖 Project Description",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text =
                        "Kavya Kanaja is a Kannada poetry application designed to preserve and promote Kannada literature digitally. Users can explore poems, listen to poem audio, save favorites and interact with AI features.",
                    fontSize = 19.sp,
                    lineHeight = 30.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "🚀 Features",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                FeatureItem("📚", "Read Kannada Poems")

                FeatureItem("🔊", "Listen using Text To Speech")

                FeatureItem("❤️", "Save Favorite Poems")

                FeatureItem("🤖", "AI Word Meaning Support")

                FeatureItem("🕒", "Recent Viewed Poems")

                FeatureItem("👤", "Poet Information")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF8F3FF)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "🛠 Technologies Used",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                TechItem("Kotlin")

                TechItem("Jetpack Compose")

                TechItem("Firebase Authentication")

                TechItem("Firebase Realtime Database")

                TechItem("Text To Speech")

                TechItem("JSON Data Storage")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "🎯 Vision",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text =
                        "To digitally preserve Kannada poetry and make literature accessible to everyone through modern mobile technology.",
                    fontSize = 19.sp,
                    lineHeight = 30.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF8F3FF)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "👩 Developer",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text =
                        "Developed as a final-year internship project for promoting Kannada literature through modern mobile technology.",
                    fontSize = 19.sp,
                    lineHeight = 30.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(24.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "📱 App Information",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Version: 1.0",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Language: Kannada & English",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Platform: Android",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun FeatureItem(
    emoji: String,
    text: String
) {

    Column {

        Row {

            Text(
                text = emoji,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                fontSize = 19.sp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Divider()

        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Composable
fun TechItem(
    text: String
) {

    Text(
        text = "• $text",
        fontSize = 20.sp,
        lineHeight = 34.sp
    )
}