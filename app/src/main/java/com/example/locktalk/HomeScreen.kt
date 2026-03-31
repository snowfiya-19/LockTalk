package com.example.locktalk

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
@OptIn(
    androidx.compose.foundation.ExperimentalFoundationApi::class,
    androidx.compose.material3.ExperimentalMaterial3Api::class
)
@Composable
fun HomeScreen(
    onChatOpen: (String) -> Unit,
    onProfileOpen: () -> Unit,
    onLockedChatsClick: () -> Unit
) {

    val chats = listOf(
        "Ruchi Joshi",
        "Rahul Sharma",
        "Team Project",
        "Ankit Verma"
    )

    // 🔥 STATE
    var selectedChat by remember { mutableStateOf<String?>(null) }
    var showSheet by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020C1B))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        // 🔝 TOP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LockTalk",
                color = Color.White,
                fontSize = 22.sp,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { onProfileOpen() }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color(0xFF5BA4F5)
                )
            }
        }

        HorizontalDivider(color = Color(0xFF1A3A6E))

        // 💬 CHAT LIST
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {

            items(chats) { chat ->

                if (!lockedChats.contains(chat)) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .combinedClickable(
                                onClick = { onChatOpen(chat) },
                                onLongClick = {
                                    selectedChat = chat
                                    showSheet = true
                                }
                            ),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF0A1628)
                        )
                    ) {

                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(chat, color = Color.White)
                        }
                    }
                }
            }
        }

        // 🔒 LOCKED CHATS BUTTON
        Button(
            onClick = { onLockedChatsClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E56C0)
            )
        ) {
            Text("🔒 Locked Chats", color = Color.White)
        }
    }

    // 🔥 WHATSAPP STYLE BOTTOM SHEET
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            containerColor = Color(0xFF0A1628)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                SheetItem("🔕 Mute") {
                    showSheet = false
                }

                HorizontalDivider(color = Color.DarkGray)

                SheetItem("🚫 Block") {
                    showSheet = false
                }

                HorizontalDivider(color = Color.DarkGray)

                SheetItem("🚨 Report") {
                    showSheet = false
                }

                HorizontalDivider(color = Color.DarkGray)

                SheetItem("🔒 Lock Chat") {
                    selectedChat?.let {
                        lockedChats.add(it)

                        Toast.makeText(
                            context,
                            "Chat Locked 🔒",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    showSheet = false
                }
            }
        }
    }
}

@Composable
fun SheetItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp)
    )
}