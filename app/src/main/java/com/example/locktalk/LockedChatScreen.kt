package com.example.locktalk
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.filled.ArrowBack

// 🔥 GLOBAL LOCKED LIST
val lockedChats = mutableStateListOf<String>()
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LockedChatScreen(
    onBack: () -> Unit,
    onChatOpen: (String) -> Unit
) {
    var selectedChat by remember { mutableStateOf<String?>(null) }
    var showSheet by remember { mutableStateOf(false) }

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
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF5BA4F5),
                modifier = Modifier.clickable { onBack() }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text("Locked Chats", color = Color.White)
        }

        // 🔒 CONTENT
        if (lockedChats.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No locked chats 🔒", color = Color.Gray)
            }
        } else {

            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(lockedChats) { chat ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .combinedClickable(
                                onClick = {
                                    onChatOpen(chat)
                                },
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
                        Text(
                            text = chat,
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    // 🔥 WHATSAPP STYLE BOTTOM SHEET
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            containerColor = Color(0xFF0A1628)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

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

                SheetItem("🔓 Remove from Locked Chats") {
                    selectedChat?.let {
                        lockedChats.remove(it)
                    }
                    showSheet = false
                }
            }
        }
    }
}