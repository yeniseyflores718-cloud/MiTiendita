package com.example.mitiendita.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataMovimientoInventario(
    val tipo: String = "",
    val cantidad: Int = 0,
    @SerialName("stock_anterior") val stockAnterior: Int = 0,
    @SerialName("stock_nuevo") val stockNuevo: Int = 0,
    val motivo: String? = null,
    val fecha: String = ""
)
