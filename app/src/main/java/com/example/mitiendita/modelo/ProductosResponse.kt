package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable

@Serializable
data class ProductosResponse(
    val exito: Boolean = false,
    val mensaje: String = "",
    val total: Int = 0,
    val productos: List<Producto> = emptyList(),   // al pedir la lista
    val producto: Producto? = null                 // al pedir un solo producto
)
