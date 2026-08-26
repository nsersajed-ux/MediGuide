package com.example.mediguide.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onAIAssistantClick: () -> Unit
) {
    var search by remember { mutableStateOf("") }

    val medicines = listOf(
        "Paracetamol (باراسيتامول)",
        "Ibuprofen (إيبوبروفين)",
        "Aspirin (أسبرين)",
        "Amoxicillin (أموكيسيسيلين)",
        "Metformin (ميتفورمين)",
        "Omeprazole (أوميبرازول)",
        "Vitamin C (فيتامين سي)"
    )

    val filteredMedicines = medicines.filter {
        it.contains(search, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "MediGuide",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAIAssistantClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("🤖 المساعد الطبي الذكي (MediGuide AI)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("بحث في دليل الأدوية...") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(filteredMedicines) { medicine ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = medicine,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}