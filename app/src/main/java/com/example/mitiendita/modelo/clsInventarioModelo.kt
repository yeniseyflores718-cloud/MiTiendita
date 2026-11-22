package com.example.mitiendita.modelo

class clsInventarioModelo {

    private val api = RetrofitCliente.apiService

    suspend fun obtenerInventario(): InventarioResponse {
        return api.obtenerInventario()
    }

    suspend fun obtenerStockBajo(): InventarioResponse {
        return api.obtenerInventario(bajo = 1)
    }

    suspend fun buscarProducto(nombre: String): InventarioResponse {
        return api.obtenerInventario(buscar = nombre)
    }

}