package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UsuarioData(
    @SerialName("id_usuario") val idUsuario: Int = 0,
    @SerialName("usuario") val usuario: String = "",
    @SerialName("nombre_usuario") val nombreUsuario: String? = null,
    @SerialName("correo") val correo: String = "",
    @SerialName("telefono") val telefono: String? = null,
    @SerialName("rol") val rol: String = ""
)

@Serializable
data class LoginResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("token") val token: String = "",
    @SerialName("usuario") val usuario: UsuarioData? = null
)
