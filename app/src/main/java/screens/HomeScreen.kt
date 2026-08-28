package com.example.mediguide.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mediguide.Medicine
import com.example.mediguide.MedicineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MedicineViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onAIAssistantClick: () -> Unit,
    onMedicineClick: (Medicine) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "دليل MediGuide") },
                actions = {
                    IconButton(onClick = onAIAssistantClick) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = "المساعد الذكي")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Button(
                onClick = onAIAssistantClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "المساعد الطبي الذكي (AI)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // حقل البحث مع تفعيل البحث فور كتابة الحروف
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.searchForMedicine(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "ابحث عن أي دواء في العالم...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "بحث")
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(searchResults) { medicine ->
                        MedicineCard(medicine = medicine, onClick = { onMedicineClick(medicine) })
                    }
                }
            }
        }
    }
}

@Composable
fun MedicineCard(medicine: Medicine, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = medicine.icon,
                contentDescription = null,modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${medicine.name} — ${medicine.arabicName}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = medicine.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}