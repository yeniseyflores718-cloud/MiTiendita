package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ReportesResponse(
    val exito: Boolean = false,
    val mensaje: String = "",
    val periodo: String = "",
    val resumen: dataResumenReporte? = null,
    @SerialName("productos_mas_vendidos")
    val productosMasVendidos: List<dataProductoMasVendido> = emptyList()
)

@Serializable
data class dataResumenReporte(
    @SerialName("total_ventas")
    val totalVentas: Int = 0,
    @SerialName("monto_total")
    val montoTotal: Double = 0.0,
    @SerialName("clientes_atendidos")
    val clientesAtendidos: Int = 0,
    @SerialName("productos_vendidos")
    val productosVendidos: Int = 0
)

@Serializable
data class dataProductoMasVendido(
    @SerialName("id_Producto")
    val idProducto: Int = 0,
    @SerialName("nombre_producto")
    val nombreProducto: String = "",
    val imagen: String? = null,
    val unidades: Int = 0
)