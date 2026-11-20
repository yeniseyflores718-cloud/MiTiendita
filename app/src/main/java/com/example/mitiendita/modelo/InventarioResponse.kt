package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable

@Serializable
data class InventarioResponse(
    val exito: Boolean = false,
    val mensaje: String = "",
    val total: Int = 0,
    val productos: List<dataProductoInventario> = emptyList(),   // al pedir la lista
    val producto: dataProductoInventario? = null                 // al pedir un solo producto
)
