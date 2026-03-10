package com.example.locktalk

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.locktalk.ui.theme.LockTalkTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔐 Block screenshots
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        setContent {

            LockTalkTheme {

                var currentScreen by remember { mutableStateOf("login") }

                when (currentScreen) {

                    "login" -> LoginScreen(
                        onLoginSuccess = {
                            currentScreen = "home"
                        },
                        onSignupClick = {
                            currentScreen = "signup"
                        }
                    )

                    "signup" -> SignupScreen(
                        onSignupSuccess = {
                            currentScreen = "login"
                        },
                        onBackToLogin = {
                            currentScreen = "login"
                        }
                    )

                    "home" -> HomeScreen(
                        onChatOpen = {
                            currentScreen = "chat"
                        },
                        onProfileOpen = {
                            currentScreen = "profile"
                        }
                    )

                    "profile" -> ProfileScreen(
                        onBack = {
                            currentScreen = "home"
                        },
                        onLogout = {
                            currentScreen = "login"
                        }
                    )


                    "chat" -> ChatScreen(
                        onBack = { currentScreen = "home" }
                    )
                }
            }
        }
    }
}