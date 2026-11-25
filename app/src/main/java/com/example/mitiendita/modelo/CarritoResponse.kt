package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CarritoResponse(
    val exito: Boolean,
    val mensaje: String? = null,
    @SerialName("total_piezas") val totalPiezas: Int = 0,
    val total: Double = 0.0,
    val carrito: List<CarritoItem> = emptyList()
)

@Serializable
data class CarritoItem(
    @SerialName("id_carrito") val idCarrito: Int,
    @SerialName("id_usuario") val idUsuario: Int,
    @SerialName("id_Producto") val idProducto: Int,
    val cantidad: Int,
    @SerialName("nombre_producto") val nombreProducto: String,
    val descripcion: String? = null,
    @SerialName("precio_v") val precioV: Double,
    @SerialName("stock_act") val stockAct: Int,
    val imagen: String? = null,
    val subtotal: Double = 0.0
)

@Serializable
data class CarritoAgregarRequest(
    @SerialName("id_usuario") val idUsuario: Int,
    @SerialName("id_producto") val idProducto: Int,
    val cantidad: Int = 1
)

@Serializable
data class CarritoActualizarRequest(
    @SerialName("id_usuario") val idUsuario: Int,
    @SerialName("id_producto") val idProducto: Int,
    val cantidad: Int
)

@Serializable
data class CarritoEliminarRequest(
    @SerialName("id_usuario") val idUsuario: Int,
    @SerialName("id_producto") val idProducto: Int? = null,
    @SerialName("id_carrito") val idCarrito: Int? = null,
    val vaciar: Boolean = false
)

@Serializable
data class CarritoAccionResponse(
    val exito: Boolean,
    val mensaje: String? = null,
    val cantidad: Int? = null
)