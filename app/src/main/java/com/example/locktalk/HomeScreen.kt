package com.example.locktalk

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun HomeScreen(
    onChatOpen: () -> Unit,
    onProfileOpen: () -> Unit
) {

    val chats = listOf(
        "Ruchi Joshi",
        "Rahul Sharma",
        "Team Project",
        "Ankit Verma"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020C1B))
            .statusBarsPadding()
    ) {

        /* -------- Top Bar -------- */

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

        /* -------- Chat List -------- */

        LazyColumn(
            modifier = Modifier.padding(12.dp)
        ) {

            items(chats) { chat ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onChatOpen() },

                    shape = RoundedCornerShape(14.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF0A1628)
                    )
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .background(
                                    Color(0xFF1E56C0),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = chat.first().toString(),
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = chat,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}