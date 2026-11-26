package com.example.mitiendita.presentador

import com.example.mitiendita.modelo.clsInicioAdminModelo
import com.example.mitiendita.vista.administrador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsInicioAdminPresentador(private val vista: administrador) {
    private val modeloInicioAdmin = clsInicioAdminModelo()
    fun cargarInicioAdmin(idUsuario: Int) {
        if (idUsuario <= 0) {
            vista.mostrarErrorInicioAdmin("ID de usuario no válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modeloInicioAdmin.obtenerInicioAdmin(idUsuario)
                }

                if (respuesta.exito) {
                    vista.mostrarInicioAdmin(respuesta)
                } else {
                    vista.mostrarErrorInicioAdmin(respuesta.mensaje ?: "No se pudieron cargar los datos del menú")
                }
            } catch (e: Exception) {
                vista.mostrarErrorInicioAdmin("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }

    }
}