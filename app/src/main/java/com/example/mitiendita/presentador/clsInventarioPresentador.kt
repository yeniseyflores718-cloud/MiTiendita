package com.example.mitiendita.presentador

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import clsInventarioModelo
import com.example.mitiendita.vista.inventario
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException

class clsInventarioPresentador(val vista: inventario) {

    val modelo = clsInventarioModelo()

    private fun obtenerIdUsuario(): Int {
        return vista.getSharedPreferences("sesion", AppCompatActivity.MODE_PRIVATE)
            .getInt("id_usuario", 0)
    }

    fun iniciar() {
        cargarInventario()
    }

    fun cargarInventario() {
        vista.lifecycleScope.launch {
            try {
                val idUsuario = obtenerIdUsuario()
                val respuesta = modelo.obtenerInventario(idUsuario)

                if (respuesta.exito) {
                    vista.mostrarProductos(respuesta.productos)
                } else {
                    vista.mostrarError(respuesta.mensaje)
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar inventario: " + e.localizedMessage)
            }
        }
    }

    fun cargarStockBajo() {
        vista.lifecycleScope.launch {
            try {
                val idUsuario = obtenerIdUsuario()
                val respuesta = modelo.obtenerStockBajo(idUsuario)

                if (respuesta.exito) {
                    vista.mostrarProductos(respuesta.productos)
                } else {
                    vista.mostrarError(respuesta.mensaje)
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar stock bajo: " + e.localizedMessage)
            }
        }
    }

    fun buscarProducto(nombre: String) {
        vista.lifecycleScope.launch {
            try {
                val idUsuario = obtenerIdUsuario()
                val respuesta = modelo.obtenerInventario(idUsuario)

                if (respuesta.exito) {
                    vista.mostrarProductos(respuesta.productos)
                } else {
                    vista.mostrarError(respuesta.mensaje)
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                vista.mostrarError("Error al buscar producto: " + e.localizedMessage)
            }
        }
    }
}