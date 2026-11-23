package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InicioAdminResponse(
    val exito: Boolean = false,
    val mensaje: String? = null,
    val ventas: VentasInicioAdmin? = null,
    val pedidos: PedidosInicioAdmin? = null,
    val productos: ProductosInicioAdmin? = null
)

@Serializable
data class VentasInicioAdmin(
    val hoy: RangoVentaAdmin? = null
)

@Serializable
data class RangoVentaAdmin(
    val monto: Double = 0.0
)

@Serializable
data class PedidosInicioAdmin(
    @SerialName("por_atender") // o "total", según corresponda al 4
    val porAtender: Int = 0
)

@Serializable
data class ProductosInicioAdmin(
    @SerialName("total_stock_bajo")
    val totalStockBajo: Int = 0
)