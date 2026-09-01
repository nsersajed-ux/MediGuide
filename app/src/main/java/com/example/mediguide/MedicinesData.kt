package com.example.mediguide

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Medication
import androidx.compose.ui.graphics.vector.ImageVector

data class Medicine(
    val name: String,
    val arabicName: String = "",
    val scientificName: String = "",
    val description: String = "",
    val uses: String = "",
    val targetPatients: String = "", // تأكد من وجود هذا السطر هنا
    val mechanism: String = "",
    val dosageForms: String = "",
    val sideEffects: String = "",
    val warnings: String = "",
    val dosageInfo: String = "",
    val emergencyInfo: String,
    val quantity: String = "",
    val strength: String = "",
    val interactions: String = "",
    val icon: ImageVector = Icons.Default.Medication
)