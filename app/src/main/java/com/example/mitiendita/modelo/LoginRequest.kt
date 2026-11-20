package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    val usuario: String,
    val contrasena: String
)