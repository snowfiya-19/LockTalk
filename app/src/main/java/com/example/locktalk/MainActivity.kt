package com.example.locktalk

import android.hardware.display.DisplayManager
import android.os.Bundle
import android.view.Display
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.locktalk.ui.theme.LockTalkTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        val displayManager = getSystemService(DISPLAY_SERVICE) as DisplayManager

        displayManager.registerDisplayListener(object : DisplayManager.DisplayListener {

            override fun onDisplayAdded(displayId: Int) {
                val display = displayManager.getDisplay(displayId)

                if (display != null && display.displayId != Display.DEFAULT_DISPLAY) {

                    Toast.makeText(
                        this@MainActivity,
                        "Screen casting is not permitted in LockTalk",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onDisplayRemoved(displayId: Int) {}

            override fun onDisplayChanged(displayId: Int) {}

        }, null)

        setContent {

            LockTalkTheme {

                var currentScreen by remember { mutableStateOf("login") }

                when (currentScreen) {

                    "login" -> LoginScreen(
                        onLoginSuccess = { currentScreen = "home" },
                        onSignupClick = { currentScreen = "signup" }
                    )

                    "signup" -> SignupScreen(
                        onSignupSuccess = { currentScreen = "login" },
                        onBackToLogin = { currentScreen = "login" }
                    )

                    "home" -> HomeScreen(
                        onChatOpen = { currentScreen = "chat" },
                        onProfileOpen = { currentScreen = "profile" }
                    )

                    "profile" -> ProfileScreen(
                        onBack = { currentScreen = "home" },
                        onLogout = { currentScreen = "login" }
                    )

                    "chat" -> ChatScreen(
                        onBack = { currentScreen = "home" }
                    )
                }
            }
        }
    }
}