package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable

@Serializable
data class CrearPedidoRequest(
    val id_usuario: Int,
    val id_metodo_pago: Int = 1,
    val tipo_entrega: String = "recoger",
    val direccion_entrega: String? = null,
    val telefono_contacto: String? = null,
    val notas: String? = null
    //
)