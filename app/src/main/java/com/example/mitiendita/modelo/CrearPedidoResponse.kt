package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable

@Serializable
data class CrearPedidoResponse(
    val exito: Boolean = false,
    val mensaje: String = "",
    val id_pedido: Int? = null,
    val codigo_pedido: String? = null,
    val estado: String? = null,
    val total: Double = 0.0
    //
)