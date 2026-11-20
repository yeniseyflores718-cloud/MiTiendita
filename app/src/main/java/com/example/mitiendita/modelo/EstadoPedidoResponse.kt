package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EstadoPedidoResponse(
    val exito: Boolean = false,
    val mensaje: String = "",
    @SerialName("id_pedido") val idPedido: Int = 0,
    @SerialName("estado_anterior") val estadoAnterior: String = "",
    @SerialName("estado_nuevo") val estadoNuevo: String = ""
)