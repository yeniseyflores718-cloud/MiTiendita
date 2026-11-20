package com.example.mitiendita.modelo

class clsBusquedaProductoModelo {

    private val api = RetrofitCliente.apiService

    suspend fun obtenerCategorias(): CategoriaResponse {
        return api.obtenerCategorias()
    }

    suspend fun filtrarProductos(nombre: String, idCategoria: Int): CategoriaResponse {
        return api.filtrarProductos(nombre, idCategoria)
    }
}