package com.example.mediguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediguide.screens.HomeScreen
import com.example.mediguide.screens.MedicineDetailScreen
import com.example.mediguide.screens.WelcomeScreen
import com.example.mediguide.ui.theme.MediGuideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MediGuideTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MediGuideAppNavigation()
                }
            }
        }
    }
}

@Composable
fun MediGuideAppNavigation() {
    val navController = rememberNavController()
    var selectedMedicine by remember { mutableStateOf<Medicine?>(null) }

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable(route = "welcome") {
            WelcomeScreen(
                onGetStartedClick = {
                    navController.navigate(route = "home") {
                        popUpTo(route = "welcome") { inclusive = true }
                    }
                }
            )
        }

        composable(route = "home") {
            HomeScreen(
                onAIAssistantClick = {
                    navController.navigate(route = "chat")
                },
                onMedicineClick = { medicine ->
                    selectedMedicine = medicine
                    navController.navigate(route = "medicine_detail")
                }
            )
        }

        composable(route = "medicine_detail") {
            selectedMedicine?.let { medicine ->
                MedicineDetailScreen(
                    medicine = medicine,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAskAI = { question ->
                        navController.navigate(route = "chat")
                    }
                )
            }
        }

        composable(route = "chat") {
            ChatMessageScreenPlaceholder(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun ChatMessageScreenPlaceholder(onBackClick: () -> Unit) {
    // يمكنك وضع محتوى شاشة المحادثة هنا
}