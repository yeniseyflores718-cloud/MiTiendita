package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagenProducto(
    val ruta: String = "",
    @SerialName("es_principal") val esPrincipal: Int = 0
)
