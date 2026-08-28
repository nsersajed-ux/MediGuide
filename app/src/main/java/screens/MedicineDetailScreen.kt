package com.example.mediguide.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mediguide.Medicine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailScreen(
    medicine: Medicine,
    onBackClick: () -> Unit,
    onAskAI: (String) -> Unit
) {
    var isFavorite by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "${medicine.name} — ${medicine.arabicName}") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع")
                    }
                },
                actions = {
                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "المفضلة",
                            tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Icon(
                    imageVector = medicine.icon,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // الاسم العلمي
            item {
                DetailSectionCard(title = "الاسم العلمي", content = medicine.scientificName)
                Spacer(modifier = Modifier.height(10.dp))
            }

            // ما هو الدواء؟
            item {
                DetailSectionCard(title = "ما هو الدواء؟", content = medicine.description)
                Spacer(modifier = Modifier.height(10.dp))
            }

            // الاستخدامات
            item {
                DetailSectionCard(title = "الاستخدامات", content = medicine.uses)
                Spacer(modifier = Modifier.height(10.dp))
            }

            // كيف يعمل؟
            item {
                DetailSectionCard(title = "كيف يعمل؟", content = medicine.mechanism)
                Spacer(modifier = Modifier.height(10.dp))
            }

            // الأشكال الدوائية
            item {
                DetailSectionCard(title = "الأشكال الدوائية", content = medicine.dosageForms)
                Spacer(modifier = Modifier.height(10.dp))
            }

            // الآثار الجانبية
            item {
                DetailSectionCard(title = "الآثار الجانبية", content = medicine.sideEffects)
                Spacer(modifier = Modifier.height(10.dp))
            }

            // التحذيرات والاحتياطات
            item {
                DetailSectionCard(title = "التحذيرات والاحتياطات ⚠️", content = medicine.warnings)
                Spacer(modifier = Modifier.height(10.dp))
            }
            // التداخلات الدوائية
            item {
                DetailSectionCard(title = "التداخلات الدوائية", content = medicine.interactions)
                Spacer(modifier = Modifier.height(10.dp))
            }

            // معلومات الجرعات
            item {
                DetailSectionCard(
                    title = "معلومات الجرعات",
                    content = "${medicine.dosageInfo}\n\n(ملاحظة: هذا محتوى تثقيفي ولا يُغني عن استشارة الطبيب أو الصيدلي)."
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // متى يجب طلب المساعدة؟
            item {
                DetailSectionCard(title = "متى يجب طلب المساعدة؟ 🚨", content = medicine.emergencyInfo)
                Spacer(modifier = Modifier.height(20.dp))
            }

            // زر المساعد الذكي والتحقق من السلامة (ممتاز للهاكاثون)
            item {
                Button(
                    onClick = { onAskAI("هل يتفاعل دواء ${medicine.name} مع حالتي أو أدوية أخرى؟") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "تحقق من سلامة الدواء مع حالتك (MediGuide AI)")
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun DetailSectionCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}