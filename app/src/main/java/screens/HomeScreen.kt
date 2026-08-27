package com.example.mediguide.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mediguide.Medicine
import com.example.mediguide.sampleMedicines

@Composable
fun HomeScreen(
    onAIAssistantClick: () -> Unit,
    onMedicineClick: (Medicine) -> Unit
) {
    var search by remember { mutableStateOf("") }
    var currentLanguage by remember { mutableStateOf("ar") }

    val appTitle = if (currentLanguage == "ar") "دليل MediGuide" else "MediGuide Guide"
    val aiButtonText = if (currentLanguage == "ar") "المساعد الطبي الذكي (AI)" else "AI Medical Assistant"
    val searchPlaceholder = if (currentLanguage == "ar") "ابحث عن الدواء..." else "Search for medicine..."

    val filteredMedicines = sampleMedicines.filter {
        it.name.contains(search, ignoreCase = true) || it.description.contains(search, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = appTitle,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAIAssistantClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "AI Icon",
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = aiButtonText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LanguageSwitchButtons(
            currentLanguage = currentLanguage,
            onArabicClick = { currentLanguage = "ar" },
            onEnglishClick = { currentLanguage = "en" }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(searchPlaceholder) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(filteredMedicines) { medicine ->
                MedicineItem(
                    medicine = medicine,
                    language = currentLanguage,
                    onClick = { onMedicineClick(medicine) }
                )
            }
        }
    }
}@Composable
fun MedicineItem(medicine: Medicine, language: String, onClick: () -> Unit) {
    val descriptionText = if (language == "ar") {
        medicine.description
    } else {
        when (medicine.name) {
            "باراسيتامول (Paracetamol)" -> "Pain reliever and fever reducer"
            "أموكسيسيلين (Amoxicillin)" -> "Strong antibiotic"
            "إيبوبروفين (Ibuprofen)" -> "Anti-inflammatory and pain reliever"
            "فيتامين سي (Vitamin C)" -> "Immunity booster (Effervescent)"
            else -> medicine.description
        }
    }

    val quantityText = if (language == "ar") {
        medicine.quantity
    } else {
        medicine.quantity
            .replace("حبة", "pills")
            .replace("زجاجة", "bottle")
            .replace("كابسولة", "capsules")
            .replace("قرص فوار", "effervescent tablets")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() }
            .clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = medicine.icon,
                contentDescription = "Medicine Icon",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = medicine.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = descriptionText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = medicine.strength,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 11.sp
                    )
                    Text(
                        text = quantityText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun LanguageSwitchButtons(
    currentLanguage: String,
    onArabicClick: () -> Unit,
    onEnglishClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        val isArabicSelected = currentLanguage == "ar"

        Button(
            onClick = onArabicClick,
            modifier = Modifier.padding(end = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isArabicSelected) MaterialTheme.colorScheme.primary else Color.Gray
            )
        ) {
            Text(text = "العربية")
        }

        Button(
            onClick = onEnglishClick,
            modifier = Modifier.padding(start = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!isArabicSelected) MaterialTheme.colorScheme.primary else Color.Gray
            )
        ) {
            Text(text = "English")
        }
    }
}