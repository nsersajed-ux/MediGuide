package com.example.mediguide

data class OpenFdaResponse(
    val meta: Meta?,
    val results: List<MedicineResult>?
)

data class Meta(
    val disclaimer: String?,
    val terms: String?,
    val license: String?,
    val last_updated: String?
)

data class MedicineResult(
    val brand_name: List<String>?,
    val generic_name: List<String>?,
    val manufacturer_name: List<String>?,
    val purpose: List<String>?,
    val active_ingredient: List<String>?,
    val indications_and_usage: List<String>?,
    val warnings: List<String>?
)