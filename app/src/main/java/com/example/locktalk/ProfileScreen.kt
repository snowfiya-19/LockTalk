package com.example.locktalk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020C1B))
            .statusBarsPadding()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF5BA4F5)
                )
            }

            Text(
                text = "Profile",
                color = Color.White,
                fontSize = 22.sp
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(Color(0xFF1E56C0), CircleShape),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "U",
                    color = Color.White,
                    fontSize = 36.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Username",
                color = Color.White,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "user@email.com",
                color = Color(0xFF5BA4F5)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onLogout() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E56C0)
                ),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp)
            ) {

                Text("Logout")
            }
        }
    }
}