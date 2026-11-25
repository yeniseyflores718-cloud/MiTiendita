package com.example.mitiendita.presentador

import com.example.mitiendita.modelo.clsCarritoModelo
import com.example.mitiendita.vista.carrito
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsCarritoPresentador(private val vista: carrito) {
    private val modeloCarrito = clsCarritoModelo()

    fun cargarCarrito(idUsuario: Int) {
        if (idUsuario <= 0) {
            vista.mostrarMensajeError("ID de usuario no válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modeloCarrito.obtenerCarrito(idUsuario)
                }

                if (respuesta.exito) {
                    vista.mostrarCarrito(respuesta)
                } else {
                    vista.mostrarMensajeError(respuesta.mensaje ?: "No se pudo cargar el carrito")
                }
            } catch (e: Exception) {
                vista.mostrarMensajeError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }

    fun actualizarCantidad(idUsuario: Int, idProducto: Int, nuevaCantidad: Int) {
        if (idUsuario <= 0 || idProducto <= 0) {
            vista.mostrarMensajeError("Datos inválidos para actualizar el carrito")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modeloCarrito.actualizarCantidad(idUsuario, idProducto, nuevaCantidad)
                }

                if (respuesta.exito) {
                    cargarCarrito(idUsuario)
                } else {
                    vista.mostrarMensajeError(respuesta.mensaje ?: "No se pudo actualizar la cantidad")
                }
            } catch (e: Exception) {
                vista.mostrarMensajeError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }

    fun eliminarProducto(idUsuario: Int, idProducto: Int) {
        if (idUsuario <= 0 || idProducto <= 0) return

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modeloCarrito.eliminarProducto(idUsuario, idProducto)
                }

                if (respuesta.exito) {
                    cargarCarrito(idUsuario)
                } else {
                    vista.mostrarMensajeError(respuesta.mensaje ?: "No se pudo eliminar el producto")
                }
            } catch (e: Exception) {
                vista.mostrarMensajeError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}