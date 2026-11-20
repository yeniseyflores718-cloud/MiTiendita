package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCategorias(
    @SerialName("id_categoria") val idCategoria: Int = 0,
    val categoria: String = ""
)
