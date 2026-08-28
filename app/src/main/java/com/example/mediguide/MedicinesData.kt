package com.example.mediguide

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Medication
import androidx.compose.ui.graphics.vector.ImageVector

// هيكل البيانات الأساسي للأدوية (تم جعل الأيقونة إجبارية وليست اختيارية لتتوافق مع شاشة التفاصيل)
data class Medicine(
    val name: String,
    val arabicName: String = "",
    val scientificName: String = "",
    val description: String = "",
    val uses: String = "",
    val mechanism: String = "",
    val dosageForms: String = "",
    val sideEffects: String = "",
    val warnings: String = "",
    val dosageInfo: String = "",
    val emergencyInfo: String = "",
    val quantity: String = "",
    val strength: String = "",
    val interactions: String = "",
    val icon: ImageVector = Icons.Default.Medication
)

// هياكل استجابة السيرفر الحقيقي (OpenFDA)
data class OpenFdaResponse(
    val results: List<OpenFdaResult>?
)

data class OpenFdaResult(
    val brand_name: List<String>?,
    val generic_name: List<String>?,
    val manufacturer_name: List<String>?
)

// قائمة الأدوية المحلية لديك
val sampleMedicines = listOf<Medicine>()