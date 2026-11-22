package com.example.mitiendita.presentador

import androidx.lifecycle.lifecycleScope
import com.example.mitiendita.modelo.clsInventarioModelo
import com.example.mitiendita.vista.inventario
import kotlinx.coroutines.launch

class clsInventarioPresentador(val vista: inventario) {

    val modelo = clsInventarioModelo()

    fun iniciar() {
        cargarInventario()
    }

    fun cargarInventario() {
        vista.lifecycleScope.launch {
            try {
                val respuesta = modelo.obtenerInventario()
                vista.mostrarProductos(respuesta.productos)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar inventario: " + e.localizedMessage)
            }
        }
    }

    fun cargarStockBajo() {
        vista.lifecycleScope.launch {
            try {
                val respuesta = modelo.obtenerStockBajo()
                vista.mostrarProductos(respuesta.productos)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar stock bajo: " + e.localizedMessage)
            }
        }
    }

    fun buscarProducto(nombre: String) {
        vista.lifecycleScope.launch {
            try {
                val respuesta = modelo.buscarProducto(nombre)
                vista.mostrarProductos(respuesta.productos)
            } catch (e: Exception) {
                vista.mostrarError("Error al buscar producto: " + e.localizedMessage)
            }
        }
    }
}