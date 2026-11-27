package com.example.mitiendita.modelo

class clsCarritoModelo {
    ///
    suspend fun obtenerCarrito(idUsuario: Int): CarritoResponse {
        return RetrofitCliente.apiService.obtenerCarrito(idUsuario)
    }

    suspend fun agregarProducto(idUsuario: Int, idProducto: Int, cantidad: Int = 1): CarritoAccionResponse {
        val request = CarritoAgregarRequest(
            idUsuario = idUsuario,
            idProducto = idProducto,
            cantidad = cantidad
        )
        return RetrofitCliente.apiService.agregarAlCarrito(request)
    }

    suspend fun actualizarCantidad(idUsuario: Int, idProducto: Int, cantidad: Int): CarritoAccionResponse {
        val request = CarritoActualizarRequest(
            idUsuario = idUsuario,
            idProducto = idProducto,
            cantidad = cantidad
        )
        return RetrofitCliente.apiService.actualizarCantidadCarrito(request)
    }

    suspend fun eliminarProducto(idUsuario: Int, idProducto: Int): RespuestaSimple {
        val request = CarritoEliminarRequest(
            idUsuario = idUsuario,
            idProducto = idProducto,
            vaciar = false
        )
        return RetrofitCliente.apiService.eliminarDelCarrito(request)
    }

    suspend fun vaciarCarrito(idUsuario: Int): RespuestaSimple {
        val request = CarritoEliminarRequest(
            idUsuario = idUsuario,
            vaciar = true
        )
        return RetrofitCliente.apiService.eliminarDelCarrito(request)
    }
}