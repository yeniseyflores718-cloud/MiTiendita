package com.example.mitiendita.modelo

import com.example.mitiendita.interfaceApi.ApiService
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.getValue
import okhttp3.MediaType.Companion.toMediaType
import kotlin.jvm.java


object RetrofitCliente {
    private const val BASE_URL = "https://sixsevenbanda.uthhbola8.com/apis/"
    const val BASE_URL_IMG = "https://yenisey.4buthh.com/miTiendita/raiz/"

    private val jsonConfig= Json {
        ignoreUnknownKeys=true
        coerceInputValues=true
        isLenient=true
    }
    val apiService : ApiService by lazy {
        val contentType="application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConfig.asConverterFactory(contentType))
            .build()
            .create(ApiService::class.java)
    }
}