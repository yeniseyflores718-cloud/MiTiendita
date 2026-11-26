package com.example.mitiendita.presentador

import androidx.lifecycle.lifecycleScope
import com.example.mitiendita.modelo.Pedido
import com.example.mitiendita.modelo.clsPedidosModelo
import com.example.mitiendita.vista.PedidosPendientes
import kotlinx.coroutines.launch

class clsPedidosPresentador(val vista: PedidosPendientes) {

    private val modelo = clsPedidosModelo()

    private var listaPedidos: List<Pedido> = emptyList()

    fun iniciar() {
        obtenerPedidos("pendiente")
    }

    fun obtenerPedidos(estado: String?) {
        vista.lifecycleScope.launch {
            try {
                val respuesta = modelo.obtenerPedidos(estado)
                listaPedidos = respuesta.pedidos
                vista.mostrarPedidos(listaPedidos)

            } catch (e: Exception) {
                vista.mostrarError(
                    "Error al cargar pedidos: " + e.localizedMessage
                )
            }
        }
    }
}