package com.example.mitiendita.presentador

import android.util.Patterns
import com.example.mitiendita.modelo.RecuperacionModelo
import com.example.mitiendita.vista.RecuperarContrato
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecuperarPresenter(
    private var vista: RecuperarContrato.Vista?,
    private val modelo: RecuperacionModelo = RecuperacionModelo()
) : RecuperarContrato.Presentador {

    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun solicitarCodigo(correo: String) {
        val mail = correo.trim().lowercase()

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            vista?.errorCorreo("Correo no válido")
            return
        }

        vista?.mostrarCargando(true)
        job = scope.launch {
            val (exito, mensaje) = modelo.solicitarCodigo(mail)
            vista?.mostrarCargando(false)
            vista?.mostrarMensaje(mensaje)
            if (exito) vista?.codigoEnviado(mail)
        }
    }

    override fun destruir() {
        job?.cancel()
        vista = null
    }
}