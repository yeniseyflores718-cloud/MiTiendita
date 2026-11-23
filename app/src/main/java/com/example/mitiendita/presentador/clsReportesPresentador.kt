package com.example.mitiendita.presentador

import com.example.mitiendita.modelo.clsReportesModelo
import com.example.mitiendita.vista.ReportesVentaspro
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsReportesPresentador(private val vista: ReportesVentaspro) {
    private val modeloReportes = clsReportesModelo()
    fun cargarReportes(idUsuario: Int, periodo: String) {
        if (idUsuario <= 0) {
            vista.mostrarMensajeError("ID de usuario no válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    when (periodo) {
                        "semana" -> modeloReportes.obtenerReportesSemana(idUsuario)
                        "mes" -> modeloReportes.obtenerReportesMes(idUsuario)
                        else -> modeloReportes.obtenerReportes(idUsuario, periodo)
                    }
                }

                if (respuesta.exito) {
                    vista.mostrarReporte(respuesta)
                } else {
                    vista.mostrarMensajeError(respuesta.mensaje ?: "No se pudieron cargar los reportes")
                }
            } catch (e: Exception) {
                vista.mostrarMensajeError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }

}