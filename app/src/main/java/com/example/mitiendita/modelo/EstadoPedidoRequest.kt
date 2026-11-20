package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EstadoPedidoRequest(
    @SerialName("id_pedido") val idPedido: Int,
    @SerialName("id_usuario") val idUsuario: Int,
    val estado: String
)
