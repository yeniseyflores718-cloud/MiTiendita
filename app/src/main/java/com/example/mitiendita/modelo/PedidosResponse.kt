package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable

@Serializable
data class PedidosResponse(
    val exito: Boolean,
    val total: Int,
    val conteo_por_estado: ConteoPedidos,
    val pedidos: List<Pedido>
)

@Serializable
data class ConteoPedidos(
    val pendiente: Int,
    val confirmado: Int,
    val preparando: Int,
    val listo: Int,
    val entregado: Int,
    val cancelado: Int
)

@Serializable
data class Pedido(
    val id_pedido: Int,
    val id_usuario: Int,
    val cliente: String,
    val fecha_pedido: String,
    val total: Double,
    val estado: String,
    val tipo_entrega: String,
    val total_articulos: Int
)