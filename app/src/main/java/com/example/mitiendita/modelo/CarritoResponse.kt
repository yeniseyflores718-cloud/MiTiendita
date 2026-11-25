package com.example.mitiendita.modelo

import com.google.gson.annotations.SerializedName

class CarritoResponse (
    val exito: Boolean,
    val mensaje: String? = null,
    @SerializedName("total_piezas") val totalPiezas: Int = 0,
    val total: Double = 0.0,
    val carrito: List<CarritoItem> = emptyList()
)

data class CarritoItem(
    @SerializedName("id_carrito") val idCarrito: Int,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_Producto") val idProducto: Int,
    val cantidad: Int,
    @SerializedName("nombre_producto") val nombreProducto: String,
    val descripcion: String? = null,
    @SerializedName("precio_v") val precioV: Double,
    @SerializedName("stock_act") val stockAct: Int,
    val imagen: String? = null,
    val subtotal: Double = 0.0
)

data class CarritoAgregarRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_producto") val idProducto: Int,
    val cantidad: Int = 1
)

data class CarritoActualizarRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_producto") val idProducto: Int,
    val cantidad: Int
)

data class CarritoEliminarRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_producto") val idProducto: Int? = null,
    @SerializedName("id_carrito") val idCarrito: Int? = null,
    val vaciar: Boolean = false
)

data class CarritoAccionResponse(
    val exito: Boolean,
    val mensaje: String? = null,
    val cantidad: Int? = null
)