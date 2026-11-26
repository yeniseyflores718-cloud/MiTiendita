package com.example.mitiendita.modelo

import com.example.mitiendita.interfaceApi.ApiService
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.getValue
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import kotlin.jvm.java


object RetrofitCliente {
    lateinit var tokenManager: TokenManager
    private const val BASE_URL = "https://sixsevenbanda.uthhbola8.com/apis/"
    const val BASE_URL_IMG = "https://yenisey.4buthh.com/miTiendita/raiz/"

    private val jsonConfig= Json {
        ignoreUnknownKeys=true
        coerceInputValues=true
        isLenient=true
    }
    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val peticion = chain.request().newBuilder()
                tokenManager.obtenerToken()?.let {
                    peticion.addHeader("Authorization", "Bearer $it")
                }
                chain.proceed(peticion.build())
            }
            .build()
    }
    val apiService : ApiService by lazy {
        val contentType="application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(jsonConfig.asConverterFactory(contentType))
            .build()
            .create(ApiService::class.java)
    }
}