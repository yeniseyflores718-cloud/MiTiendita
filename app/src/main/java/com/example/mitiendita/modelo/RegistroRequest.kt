    package com.example.mitiendita.modelo

    import kotlinx.serialization.Serializable

    @Serializable
    data class RegistroRequest(
        val usuario: String? = null,      // opcional: si va null, la API lo genera
        val nombre: String,
        val correo: String,
        val contrasena: String,
        val telefono: String? = null,
        val direccion: String? = null
    )
