package com.example.mitiendita.presentador

import androidx.lifecycle.lifecycleScope
import com.example.mitiendita.modelo.clsBusquedaProductoModelo
import com.example.mitiendita.modelo.dataCategorias
import com.example.mitiendita.modelo.Producto
import com.example.mitiendita.modelo.RetrofitCliente
import com.example.mitiendita.vista.Busqueda_de_producto
import kotlinx.coroutines.launch

class clsBusquedaProductoPresentador(val vista: Busqueda_de_producto) {

    private val modelo = clsBusquedaProductoModelo()

    private var listaCategorias: List<dataCategorias> = emptyList()
    private var listaProductos: List<Producto> = emptyList()

    fun iniciar() {
        vista.lifecycleScope.launch {
            try {
                val respuesta = modelo.obtenerCategorias()
                listaCategorias = respuesta.categorias
                vista.mostrarCategorias(listaCategorias)
                filtrarProductos("", 0)

            } catch (e: Exception) {
                vista.mostrarError("Error al cargar categorías: " + e.localizedMessage)
            }
        }
    }

    fun filtrarProductos(nombre: String, idCategoria: Int) {
        vista.lifecycleScope.launch {
            try {
                val respuesta = modelo.filtrarProductos(nombre, idCategoria)
                listaProductos = respuesta.productos
                vista.mostrarProductos(listaProductos)

            } catch (e: Exception) {
                vista.mostrarError("Error al cargar productos: " + e.localizedMessage)
            }
        }
    }

    fun seleccionarCategoria(posicion: Int, nombre: String) {

        val idCategoria = if (posicion == 0) {
            0
        } else {
            listaCategorias[posicion - 1].idCategoria
        }

        filtrarProductos(nombre, idCategoria)
    }
    fun verificarSesion() {
        val haySesion = RetrofitCliente.tokenManager.obtenerToken() != null
        vista.mostrarBotonIniciarSesion(!haySesion)

        if (haySesion) {
            // Según el rol guardado, mandamos a cada quien a su pantalla
            when (RetrofitCliente.tokenManager.obtenerRol().lowercase()) {
                "administrador" -> vista.irAAdministrador()
                "cajero", "vendedor", "empleado" -> vista.irAEmpleado()
                // cliente: se queda en el catálogo
            }
        }
    }
}