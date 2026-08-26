package com.example.mediguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediguide.screens.HomeScreen
import com.example.mediguide.screens.WelcomeScreen
import com.example.mediguide.ui.theme.MediGuideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MediGuideTheme {
                MediGuideAppNavigation()
            }
        }
    }
}

@Composable
fun MediGuideAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                onGetStartedClick = {
                    navController.navigate("home") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(
                onAIAssistantClick = {
                    // سنقوم بربط شاشة المحادثة هنا في الخطوة القادمة
                }
            )
        }
    }
}