package com.example.locktalk

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding

/* 🔐 Message model */

data class Message(
    val text: String? = null,
    val isSentByMe: Boolean,
    val isImage: Boolean = false,
    var viewed: Boolean = false
)

@Composable
fun ChatScreen(chatName: String, onBack: () -> Unit) {

    var messageText by remember { mutableStateOf(TextFieldValue("")) }
    var showWatermark by remember { mutableStateOf(false) }

    val messagesMap = remember {
        mutableStateMapOf<String, MutableList<Message>>()
    }

    val messages = messagesMap.getOrPut(chatName) {
        mutableStateListOf(
            Message("Hey!", false),
            Message("Hi 👋", true)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020C1B))
    ) {

        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .statusBarsPadding(),   // ✅ FIX HERE
            ) {

                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF5BA4F5)
                    )
                }

                Text(
                    text = chatName,
                    color = Color.White
                )
            }

            HorizontalDivider(color = Color(0xFF1A3A6E))

            /* -------- Chat Messages -------- */

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                reverseLayout = true
            ) {

                items(messages.reversed()) { message ->

                    MessageBubble(
                        message = message,
                        onImageOpen = { showWatermark = true }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()   // ✅ CRITICAL FIX
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },

                    modifier = Modifier.weight(1f),

                    placeholder = {
                        Text("Type a message", color = Color.Gray)
                    },

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF0A1628),
                        unfocusedContainerColor = Color(0xFF0A1628),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(6.dp))

                /* 📷 Send view-once image */

                Button(
                    onClick = {
                        messages.add(
                            Message(
                                isSentByMe = true,
                                isImage = true
                            )
                        )
                    }
                ) {
                    Text("📷")
                }

                Spacer(modifier = Modifier.width(6.dp))

                /* Send text */

                Button(
                    onClick = {
                        if (messageText.text.isNotBlank()) {

                            messages.add(
                                Message(
                                    text = messageText.text,
                                    isSentByMe = true
                                )
                            )

                            messageText = TextFieldValue("")
                        }
                    }
                ) {
                    Text("Send")
                }
            }
        }

        /* 🔐 Watermark overlay */

        if (showWatermark) {

            WatermarkOverlay(
                username = "Ruchi Joshi",
                onClose = { showWatermark = false }
            )
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    onImageOpen: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),

        horizontalArrangement =
            if (message.isSentByMe) Arrangement.End else Arrangement.Start
    ) {

        Box(
            modifier = Modifier
                .background(
                    color =
                        if (message.isSentByMe)
                            Color(0xFF1E56C0)
                        else
                            Color(0xFF0A1628),

                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
                .widthIn(max = 250.dp)
        ) {

            when {

                message.isImage -> {

                    if (message.viewed) {

                        Text(
                            "📵 Image expired",
                            color = Color.White
                        )

                    } else {

                        Text(
                            text = "📷 View image",
                            color = Color.White,
                            modifier = Modifier.clickable {

                                message.viewed = true
                                onImageOpen()
                            }
                        )
                    }
                }

                message.text != null -> {

                    Text(
                        text = message.text,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun WatermarkOverlay(
    username: String,
    onClose: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
    ) {

        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.Center)
                .background(Color.DarkGray, RoundedCornerShape(12.dp)),

            contentAlignment = Alignment.Center
        ) {

            Text(
                "Sensitive Media",
                color = Color.White
            )
        }

        Text(
            text = "$username • ${System.currentTimeMillis()}",
            color = Color.White.copy(alpha = 0.6f),

            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

        Button(
            onClick = onClose,

            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("Close")
        }
    }
}