package com.example.mitiendita.presentador

import com.example.mitiendita.modelo.SesionLocal

interface CuentaView {
    fun mostrarPerfil(nombre: String, correo: String, telefono: String, iniciales: String)
    fun irALogin()
}

class cuentaPresentador(
    private val view: CuentaView,
    private val sesion: SesionLocal
) {
    fun cargarPerfil() {
        val p = sesion.perfil()
        if (p == null) { view.irALogin(); return }

        val iniciales = p.nombre.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .joinToString("") { it.first().uppercase() }
            .ifEmpty { "?" }

        view.mostrarPerfil(
            nombre = p.nombre,
            correo = p.correo,
            telefono = p.telefono?.takeIf { it.isNotBlank() } ?: "Sin registrar",
            iniciales = iniciales
        )
    }

    fun cerrarSesion() {
        sesion.cerrar()
        view.irALogin()
    }
}