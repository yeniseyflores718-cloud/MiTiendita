package com.example.mitiendita.presentador

import com.example.mitiendita.modelo.CrearPedidoRequest
import com.example.mitiendita.modelo.clsConfirmarPedidoModelo
import com.example.mitiendita.vista.confirmarPedido
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsConfirmarPedidoPresentador(private val vista: confirmarPedido) {
    private val modeloPedido = clsConfirmarPedidoModelo()

    fun cargarResumen(idUsuario: Int) {
        if (idUsuario <= 0) {
            vista.mostrarMensajeError("ID de usuario no válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modeloPedido.obtenerResumenCarrito(idUsuario)
                }

                if (respuesta.exito) {
                    vista.mostrarResumen(respuesta)
                } else {
                    vista.mostrarMensajeError(respuesta.mensaje ?: "No se pudo cargar el resumen")
                }
            } catch (_: CancellationException) {
            } catch (e: Exception) {
                vista.mostrarMensajeError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }

    fun confirmarPedido(request: CrearPedidoRequest) {
        if (request.id_usuario <= 0) {
            vista.mostrarMensajeError("ID de usuario no válido")
            return
        }

        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modeloPedido.crearPedido(request)
                }

                vista.mostrarCargando(false)
                if (respuesta.exito) {
                    val folio = respuesta.codigo_pedido
                        ?: respuesta.id_pedido?.let { "#${it.toString().padStart(4, '0')}" }
                        ?: "Registrado"
                    val estado = respuesta.estado ?: "pendiente"
                    vista.pedidoRegistrado(
                        folio = folio,
                        metodoPagoTexto = "Efectivo al recoger",
                        estado = estado,
                        total = respuesta.total
                    )
                    //
                } else {
                    vista.mostrarMensajeError(respuesta.mensaje.ifEmpty { "No se pudo registrar el pedido" })
                }
            } catch (_: CancellationException) {
                vista.mostrarCargando(false)
            } catch (e: Exception) {
                vista.mostrarCargando(false)
                vista.mostrarMensajeError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}