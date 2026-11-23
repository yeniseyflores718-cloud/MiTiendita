package com.example.mitiendita.modelo

class clsDetalleProductoModelo {

    private val api = RetrofitCliente.apiService

    suspend fun obtenerProducto(idProducto: Int): ProductosResponse {
        return api.obtenerProducto(idProducto)
    }
}