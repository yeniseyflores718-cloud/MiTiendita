package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable

@Serializable
data class RegistroResponse(
    val exito: Boolean,
    val mensaje: String,
    val id_usuario: Int? = null,
    val usuario: String? = null,
    val rol: String? = null
)
