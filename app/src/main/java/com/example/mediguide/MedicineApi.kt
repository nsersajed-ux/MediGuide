package com.example.mediguide

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MedicineApiService {
    @GET("drug/label.json")
    suspend fun searchMedicine(
        @Query("search") query: String,
        @Query("limit") limit: Int = 10
    ): OpenFdaResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://api.fda.gov/"

    val apiService: MedicineApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MedicineApiService::class.java)
    }
}