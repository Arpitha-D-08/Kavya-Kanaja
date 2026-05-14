package com.example.kavyakanaja.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kavyakanaja.data.GroqManager
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

@Composable
fun ChatBotScreen() {

    var message by remember {
        mutableStateOf("")
    }

    val chatList = remember {
        mutableStateListOf<ChatMessage>()
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Kavya AI Assistant 🤖",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(chatList) { chat ->

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = if (chat.isUser)
                        Alignment.CenterEnd
                    else
                        Alignment.CenterStart
                ) {

                    Box(
                        modifier = Modifier
                            .background(
                                color = if (chat.isUser)
                                    Color(0xFF6A1B9A)
                                else
                                    Color.LightGray,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(12.dp)
                    ) {

                        Text(
                            text = chat.text,
                            color = if (chat.isUser)
                                Color.White
                            else
                                Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Ask something...")
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {

                    if (message.isNotEmpty()) {

                        val userMessage = message

                        chatList.add(
                            ChatMessage(userMessage, true)
                        )

                        message = ""

                        scope.launch {

                            val response =
                                GroqManager.getResponse(userMessage)

                            chatList.add(
                                ChatMessage(response, false)
                            )
                        }
                    }
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send"
                )
            }
        }
    }
}