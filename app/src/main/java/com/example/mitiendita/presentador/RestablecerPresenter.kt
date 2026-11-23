package com.example.mitiendita.presentador

import com.example.mitiendita.modelo.RecuperacionModelo
import com.example.mitiendita.vista.RestablecerContrato
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestablecerPresenter(
    private var vista: RestablecerContrato.Vista?,
    private val modelo: RecuperacionModelo = RecuperacionModelo()
) : RestablecerContrato.Presentador {

    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun restablecer(correo: String, codigo: String, nueva: String, confirmacion: String) {
        val cod = codigo.trim()
        var valido = true

        if (!Regex("^[0-9]{6}$").matches(cod)) {
            vista?.errorCodigo("El código tiene 6 dígitos")
            valido = false
        }
        if (nueva.length < 8 || nueva.length > 72) {
            vista?.errorContrasena("La contraseña debe tener de 8 a 72 caracteres")
            valido = false
        }
        if (nueva != confirmacion) {
            vista?.errorConfirmacion("Las contraseñas no coinciden")
            valido = false
        }
        if (!valido) return

        vista?.mostrarCargando(true)
        job = scope.launch {
            val (exito, mensaje) = modelo.restablecer(correo, cod, nueva)
            vista?.mostrarCargando(false)
            vista?.mostrarMensaje(mensaje)
            if (exito) vista?.contrasenaActualizada()
        }
    }

    override fun destruir() {
        job?.cancel()
        vista = null
    }
}