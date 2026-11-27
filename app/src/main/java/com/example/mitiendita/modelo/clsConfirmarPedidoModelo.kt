package com.example.mitiendita.modelo

class clsConfirmarPedidoModelo {
    //
    suspend fun obtenerResumenCarrito(idUsuario: Int): CarritoResponse {
        return RetrofitCliente.apiService.obtenerCarrito(idUsuario)
    }

    suspend fun crearPedido(request: CrearPedidoRequest): CrearPedidoResponse {
        return RetrofitCliente.apiService.crearPedido(request)
    }
}