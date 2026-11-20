package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UsuarioData(
    @SerialName("id_usuario") val idUsuario: Int = 0,
    val usuario: String = "",
    @SerialName("nombre_usuario") val nombreUsuario: String = "",
    val correo: String = "",
    val rol: String = ""
)

@Serializable
data class LoginResponse(
    val exito: Boolean = false,
    val mensaje: String = "",
    val token: String? = null,
    val usuario: UsuarioData? = null
)
