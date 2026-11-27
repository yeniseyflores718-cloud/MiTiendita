package com.example.mitiendita.modelo

import android.content.Context

class clsPedidoRegistradoModelo(private val context: Context) {

    fun obtenerDatos(
        idUsuarioExtra: Int,
        idPedido: Int,
        folioExtra: String?,
        totalExtra: Double
    ): PedidoRegistradoResponse {
        val prefs = context.getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE)
        val idUsuarioPref = prefs.getInt("id_usuario", prefs.getInt("idUsuario", 1))
        val idUsuarioFinal = if (idUsuarioExtra > 0) idUsuarioExtra else idUsuarioPref

        val folioFinal = when {
            !folioExtra.isNullOrBlank() -> folioExtra
            idPedido > 0 -> "#${idPedido.toString().padStart(4, '0')}"
            else -> "#0042"
        }

        return PedidoRegistradoResponse(
            idUsuario = idUsuarioFinal,
            folio = folioFinal,
            total = totalExtra
        )
    }
}