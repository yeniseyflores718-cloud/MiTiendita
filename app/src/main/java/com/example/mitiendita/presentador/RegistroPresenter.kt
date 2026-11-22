package com.example.mitiendita.presentador

import android.util.Patterns
import com.example.mitiendita.modelo.RegistroModelo
import com.example.mitiendita.modelo.RegistroRequest
import com.example.mitiendita.modelo.ResultadoRegistro
import com.example.mitiendita.vista.RegistroContrato
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegistroPresenter(
    private var vista: RegistroContrato.Vista?,
    private val modelo: RegistroModelo = RegistroModelo()
) : RegistroContrato.Presentador {

    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun registrar(
        nombre: String,
        correo: String,
        telefono: String,
        contrasena: String,
        confirmacion: String
    ) {
        val nom = nombre.trim()
        val mail = correo.trim().lowercase()
        val tel = telefono.trim()
        var valido = true

        if (nom.length < 2 || nom.length > 100) {
            vista?.errorNombre("El nombre debe tener de 2 a 100 caracteres")
            valido = false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches() || mail.length > 100) {
            vista?.errorCorreo("Correo no válido")
            valido = false
        }
        if (tel.isNotEmpty() && !Regex("^[0-9]{10}$").matches(tel)) {
            vista?.errorTelefono("El teléfono debe tener 10 dígitos")
            valido = false
        }
        if (contrasena.length < 8 || contrasena.length > 72) {
            vista?.errorContrasena("La contraseña debe tener de 8 a 72 caracteres")
            valido = false
        }
        if (contrasena != confirmacion) {
            vista?.errorConfirmacion("Las contraseñas no coinciden")
            valido = false
        }
        if (!valido) return

        vista?.mostrarCargando(true)

        val request = RegistroRequest(
            nombre = nom,
            correo = mail,
            contrasena = contrasena,
            telefono = if (tel.isEmpty()) null else tel
        )

        job = scope.launch {
            val resultado = modelo.registrar(request)
            vista?.mostrarCargando(false)

            when (resultado) {
                is ResultadoRegistro.Exito -> {
                    vista?.mostrarMensaje(resultado.mensaje)
                    vista?.registroExitoso()
                }
                is ResultadoRegistro.Fallo -> vista?.mostrarMensaje(resultado.mensaje)
            }
        }
    }

    override fun destruir() {
        job?.cancel()
        vista = null
    }
}