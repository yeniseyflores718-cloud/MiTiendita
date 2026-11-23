package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestablecerRequest(
    val correo: String,
    val codigo: String,
    @SerialName("nueva_contrasena") val nuevaContrasena: String
)