package com.example.locktalk

import android.hardware.display.DisplayManager
import android.os.Bundle
import android.view.Display
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.locktalk.ui.theme.LockTalkTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔐 Block screenshots
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        val displayManager = getSystemService(DISPLAY_SERVICE) as DisplayManager

        setContent {

            var selectedChat by remember { mutableStateOf("") }
            var currentScreen by remember { mutableStateOf("login") }
            var isAuthenticated by remember { mutableStateOf(false) }
            var isCasting by remember { mutableStateOf(false) }

            // 🔍 Detect casting
            DisposableEffect(Unit) {
                val listener = object : DisplayManager.DisplayListener {
                    override fun onDisplayAdded(displayId: Int) {
                        val display = displayManager.getDisplay(displayId)
                        if (display != null && display.displayId != Display.DEFAULT_DISPLAY) {
                            isCasting = true
                            Toast.makeText(
                                this@MainActivity,
                                "Screen casting is not permitted",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onDisplayRemoved(displayId: Int) {
                        isCasting = false
                    }

                    override fun onDisplayChanged(displayId: Int) {}
                }

                displayManager.registerDisplayListener(listener, null)

                onDispose {
                    displayManager.unregisterDisplayListener(listener)
                }
            }

            LockTalkTheme {

                if (isCasting) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Screen sharing blocked", color = Color.White)
                    }
                } else {

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
                            onChatOpen = {
                                selectedChat = it
                                currentScreen = "chat"
                            },
                            onProfileOpen = { currentScreen = "profile" },
                            onLockedChatsClick = {
                                isAuthenticated = false
                                currentScreen = "locked"
                            }
                        )

                        "chat" -> ChatScreen(
                            chatName = selectedChat,
                            onBack = { currentScreen = "home" }
                        )

                        "locked" -> {

                            val activity = this@MainActivity

                            LaunchedEffect(Unit) {
                                if (!isAuthenticated) {
                                    BiometricHelper.showBiometricPrompt(activity) {
                                        isAuthenticated = true
                                    }
                                }
                            }

                            if (isAuthenticated) {
                                LockedChatScreen(
                                    onBack = {
                                        isAuthenticated = false
                                        currentScreen = "home"
                                    },
                                    onChatOpen = {
                                        selectedChat = it
                                        isAuthenticated = false
                                        currentScreen = "chat"
                                    }
                                )
                            } else {
                                // Optional loading screen while biometric shows
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Authenticating...", color = Color.White)
                                }
                            }
                        }

                        "profile" -> ProfileScreen(
                            onBack = { currentScreen = "home" },
                            onLogout = { currentScreen = "login" }
                        )
                    }
                }
            }
        }
    }
}