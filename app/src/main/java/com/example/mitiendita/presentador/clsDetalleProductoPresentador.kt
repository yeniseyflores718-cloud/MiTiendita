package com.example.mitiendita.presentador

import androidx.lifecycle.lifecycleScope
import com.example.mitiendita.modelo.clsDetalleProductoModelo
import com.example.mitiendita.vista.DescripcionDeProducto
import kotlinx.coroutines.launch

class clsDetalleProductoPresentador(val vista: DescripcionDeProducto) {

    private val modelo = clsDetalleProductoModelo()

    fun obtenerProducto(idProducto: Int) {
        vista.lifecycleScope.launch {
            try {
                val respuesta = modelo.obtenerProducto(idProducto)

                if (respuesta.exito && respuesta.producto != null) {
                    vista.mostrarProducto(respuesta.producto)
                } else {
                    vista.mostrarError("No se encontró el producto")
                }

            } catch (e: Exception) {
                vista.mostrarError("Error al cargar el producto: " + e.localizedMessage)
            }
        }
    }
}