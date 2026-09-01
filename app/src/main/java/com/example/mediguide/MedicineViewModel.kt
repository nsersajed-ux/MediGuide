package com.example.mediguide

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MedicineViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchResults = MutableStateFlow<List<Medicine>>(emptyList())
    val searchResults: StateFlow<List<Medicine>> = _searchResults

    fun searchForMedicine(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val localResults = painRelieversList.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.arabicName.contains(query, ignoreCase = true) ||
                            it.scientificName.contains(query, ignoreCase = true)
                }

                if (localResults.isNotEmpty()) {
                    _searchResults.value = localResults
                } else {
                    _searchResults.value = emptyList()
                }
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}