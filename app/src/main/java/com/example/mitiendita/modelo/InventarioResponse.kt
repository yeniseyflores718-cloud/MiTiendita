package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InventarioResponse(
    val resumen: ResumenInventario = ResumenInventario(),
    val productos: List<dataProductoInventario> = emptyList()
)

@Serializable
data class ResumenInventario(
    @SerialName("total_productos") val totalProductos: Int = 0,
    val agotados: Int = 0,
    @SerialName("stock_bajo") val stockBajo: Int = 0,
    @SerialName("valor_inventario") val valorInventario: Double = 0.0
)