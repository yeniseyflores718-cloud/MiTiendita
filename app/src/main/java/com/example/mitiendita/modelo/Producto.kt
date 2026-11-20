package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    @SerialName("id_Producto") val idProducto: Int = 0,
    @SerialName("nombre_producto") val nombreProducto: String = "",
    val descripcion: String = "",
    @SerialName("precio_v") val precioV: Double = 0.0,
    @SerialName("stock_act") val stockAct: Int = 0,
    @SerialName("id_categoria") val idCategoria: Int = 0,
    val categoria: String = "",
    val imagen: String? = null,
    val imagenes: List<ImagenProducto> = emptyList()
)
