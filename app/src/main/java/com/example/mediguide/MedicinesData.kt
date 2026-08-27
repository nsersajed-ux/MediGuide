package com.example.mediguide

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Bloodtype // لتعبير عن فوار أو كبسولة فوارة
import androidx.compose.material.icons.filled.Vaccines // لتعبير عن حقنة إذا لزم الأمر
import androidx.compose.material.icons.filled.HealthAndSafety // أيقونة عامة للصحة
import androidx.compose.ui.graphics.vector.ImageVector

data class Medicine(
    val name: String,
    val description: String,
    val icon: ImageVector,
    val quantity: String,
    val strength: String
)

val sampleMedicines = listOf(
    Medicine(
        name = "باراسيتامول (Paracetamol)",
        description = "مسكن للألم وخافض للحرارة",
        icon = Icons.Default.Medication, // أيقونة حبوب
        quantity = "20 حبة",
        strength = "500 ملغم"
    ),
    Medicine(
        name = "أموكسيسيلين (Amoxicillin)",
        description = "مضاد حيوي قوي",
        icon = Icons.Default.LocalDrink, // أيقونة شراب
        quantity = "زجاجة 100 مل",
        strength = "250 ملغم / 5 مل"
    ),
    Medicine(
        name = "إيبوبروفين (Ibuprofen)",
        description = "مضاد للالتهاب ومسكن",
        icon = Icons.Default.LocalPharmacy, // أيقونة كبسولات
        quantity = "16 كبسولة",
        strength = "400 ملغم"
    ),
    Medicine(
        name = "فيتامين سي (Vitamin C)",
        description = "مقوي للمناعة (فوار)",
        icon = Icons.Default.Bloodtype, // أيقونة قطرة/فوار
        quantity = "20 قرص فوار",
        strength = "1000 ملغم"
    ),
    Medicine(
        name = "بانادول إكسترا (Panadol Extra)",
        description = "مسكن قوي جداً للصداع",
        icon = Icons.Default.Healing, // أيقونة شريط أدوية
        quantity = "24 حبة",
        strength = "500 ملغم / 65 ملغم كافيين"
    ),
    Medicine(
        name = "أوميبرازول (Omeprazole)",
        description = "علاج حموضة المعدة والارتجاع",
        icon = Icons.Default.MedicalServices, // أيقونة طبية عامة
        quantity = "14 كبسولة",
        strength = "20 ملغم"
    ),
    Medicine(
        name = "إمبيسلين (Ampicillin)",
        description = "مضاد حيوي (حقن)",
        icon = Icons.Default.Vaccines, // أيقونة حقنة
        quantity = "فالتين 1 غرام",
        strength = "1 غرام"
    ),
    Medicine(
        name = "لوراتادين (Loratadine)",
        description = "مضاد للحساسية (حبوب)",
        icon = Icons.Default.Medication, // أيقونة حبوب
        quantity = "10 حبات",
        strength = "10 ملغم"
    )
)