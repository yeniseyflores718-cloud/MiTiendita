package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable

@Serializable
data class CategoriaResponse(
    val exito: Boolean = false,
    val mensaje: String = "",
    val total: Int = 0,
    val categorias: List<dataCategorias> = emptyList(),
    val productos: List<Producto> = emptyList()
)