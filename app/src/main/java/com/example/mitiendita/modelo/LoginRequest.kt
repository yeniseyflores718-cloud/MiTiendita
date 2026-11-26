package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    @SerialName("correo") val correo: String = "",
    @SerialName("contrasena") val contrasena: String = ""
)