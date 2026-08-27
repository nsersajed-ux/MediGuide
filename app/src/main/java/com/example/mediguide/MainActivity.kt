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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                    // Handle medicine click here if needed
                }
            )
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
    // يمكنك وضع أزرار الترجمة هنا أو في الواجهة الرئيسية حسب رغبتك
}

@Composable
fun LanguageSwitchButtons(
    onArabicClick: () -> Unit,
    onEnglishClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onArabicClick,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(text = "العربية")
        }

        Button(
            onClick = onEnglishClick,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "English")
        }
    }
}