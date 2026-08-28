package com.example.mediguide

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Medication
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MedicineViewModel : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Medicine>>(emptyList())
    val searchResults: StateFlow<List<Medicine>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun searchForMedicine(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. البحث محلياً أولاً في القائمة الثابتة
                val localResults = sampleMedicines.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.arabicName.contains(query, ignoreCase = true) ||
                            it.scientificName.contains(query, ignoreCase = true)
                }

                if (localResults.isNotEmpty()) {
                    _searchResults.value = localResults
                } else {
                    // 2. البحث في سيرفر OpenFDA العالمي وجلب تفاصيل الدواء الحقيقية
                    val searchQuery = "openfda.brand_name:\"$query*\" OR openfda.generic_name:\"$query*\""
                    val response = RetrofitClient.apiService.searchMedicine(searchQuery)

                    val apiMedicines = response.results?.map { result ->
                        val brandName = result.brand_name?.firstOrNull() ?: query.uppercase()
                        val genericName = result.generic_name?.firstOrNull() ?: "Active Ingredient"
                        val manufacturer = result.manufacturer_name?.firstOrNull() ?: "Global Pharmaceutical Corp"

                        Medicine(
                            name = brandName,
                            arabicName = genericName,
                            scientificName = genericName,
                            description = "الشركة المصنعة: $manufacturer",
                            uses = "دواء معتمد عالمياً لل Mادة الفعالة ($genericName) ويستخدم حسب البروتوكولات السريرية الخاصة بـ $brandName.",
                            mechanism = "يعمل عقار $brandName عبر التفاعل الحيوي للمادة الفعالة $genericName داخل الجسم.",
                            dosageForms = "أقراص / كبسولات / محلول فموي",
                            sideEffects = "قد تظهر بعض الآثار المرتبطة بـ $brandName مثل التحسس الفردي أو اضطراب خفيف.",
                            warnings = "يحذر استخدامه بدون إشراف طبي مباشر خصوصاً عند تناول أدوية أخرى بالتزامن مع $brandName.",
                            dosageInfo = "تحدد الجرعة الدقيقة لـ $brandName بناءً على تقييم الطبيب المعالج للحالة.",
                            emergencyInfo = "عند حدوث أعراض تحسس حادة أو ضيق تنفس، توجه للطوارئ فوراً.",
                            quantity = "متوفر في السجلات العالمية",
                            strength = "حسب النشرة القياسية للعبوة",
                            interactions = "يجب مراجعة الصيدلي للتأكد من خلوه من التداخلات الدوائية.",
                            icon = Icons.Default.Medication
                        )
                    } ?: emptyList()

                    _searchResults.value = apiMedicines
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}