package com.example.mitiendita.presentador

import com.example.mitiendita.modelo.PedidoRegistradoResponse
import com.example.mitiendita.modelo.clsPedidoRegistradoModelo
import com.example.mitiendita.vista.pedido_registrado
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsPedidoRegistradoPresentador(
    private val vista: pedido_registrado,
    private val modelo: clsPedidoRegistradoModelo = clsPedidoRegistradoModelo(vista)
) {
    private var idUsuarioActual: Int = 1

    fun cargarPedidoRegistrado(
        idUsuarioExtra: Int,
        idPedido: Int,
        folioExtra: String?,
        totalExtra: Double
    ) {
        if (totalExtra < 0.0) {
            vista.mostrarMensajeError("Total del pedido no válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta: PedidoRegistradoResponse = withContext(Dispatchers.IO) {
                    modelo.obtenerDatos(idUsuarioExtra, idPedido, folioExtra, totalExtra)
                }

                idUsuarioActual = respuesta.idUsuario
                vista.pintarPedido(respuesta.folio, respuesta.total)
            } catch (e: Exception) {
                vista.mostrarMensajeError(
                    "Error al cargar el comprobante: ${e.localizedMessage ?: "Error desconocido"}"
                )
            }
        }
    }

    fun iniciarAnimacionTresSegundos() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                vista.iniciarLottie(3000L)
                delay(3000L)
                vista.terminarLottie()
            } catch (_: Exception) {
            }
        }
    }

    fun obtenerIdUsuario(): Int = idUsuarioActual
}