package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataProductoInventario(
    @SerialName("id_Producto") val idProducto: Int = 0,
    @SerialName("nombre_producto") val nombreProducto: String = "",
    @SerialName("stock_act") val stockAct: Int = 0,
    @SerialName("stock_min") val stockMin: Int? = null,
    @SerialName("precio_c") val precioC: Double = 0.0,
    @SerialName("precio_v") val precioV: Double = 0.0,
    val nivel: String = "",
    val categoria: String? = null
)