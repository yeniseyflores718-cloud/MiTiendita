package com.example.mitiendita.modelo

class clsPedidosModelo {

    private val api = RetrofitCliente.apiService

    suspend fun obtenerPedidos(estado: String?): PedidosResponse {
        val respuesta = api.obtenerPedidos(estado)

        if (respuesta.isSuccessful) {
            return respuesta.body()!!
        } else {
            throw Exception("Error al obtener los pedidos")
        }
    }
}